package com.MITProjectService.bot.Services.impl;import com.MITProjectCommon.common.PayHereUtils;import com.MITProjectService.bot.Services.PayHereService;import com.MITProjectService.bot.Services.SnUserService;import com.MITProjectService.bot.dao.jpaRepos.PayHereRepository;import com.MITProjectService.bot.domain.SnUser;import com.MITProjectService.bot.domain.paymentGateWay.PayHereModel;import com.MITProjectService.bot.request.PayHereRequest;import com.MITProjectService.bot.request.PaymentRequest;import jakarta.annotation.Resource;import org.springframework.beans.BeanUtils;import org.springframework.beans.factory.annotation.Value;import org.springframework.stereotype.Service;import org.springframework.transaction.annotation.Transactional;import java.security.NoSuchAlgorithmException;import java.util.Optional;@Servicepublic class PayHereServiceImpl implements PayHereService {    @Value("${payhere.merchant.id}")    private String merchantId;    @Value("${payhere.merchant.secret}")    private String merchantSecret;    @Value("${payhere.sandbox.url}")    private String payHereUrl;    @Value("${payhere.success.url}")    private String successUrl;    @Value("${payhere.cancel.url}")    private String cancelUrl;    @Value("${payhere.notify.url}")    private String notifyUrl;    @Resource    private PayHereRepository payHereRepository;    @Resource    private SnUserService snUserService;    /**     * Processes a payment request by retrieving user and booking details,     * preparing the PayHere payment request, saving the transaction locally,     * and generating a PayHere payment URL.     *     * @param paymentRequest The payment request object containing user and booking information.     * @return The PayHere payment URL where the user can complete the payment.     * @throws RuntimeException If the user or booking is not found or if an error occurs during processing.     */    @Override    @Transactional    public String doPayment(PaymentRequest paymentRequest) {        try{            // Retrieve user details based on the provided user ID            Optional<SnUser> user = snUserService.getUserByID(paymentRequest.getUserId());            if(user.isEmpty()){                throw new RuntimeException("User not found");            }            // Populate payment request details with user and booking information            paymentRequest.setEmail(user.get().getEmail());            paymentRequest.setPhone(user.get().getPhone());            paymentRequest.setFirstName(user.get().getFirstName());            paymentRequest.setLastName(user.get().getUserName());            paymentRequest.setAmount(paymentRequest.getBooking().getPrice());            paymentRequest.setOrderId(paymentRequest.getBooking().getOrderId());            paymentRequest.setCurrency("LKR");            // save the transaction in local            PayHereRequest request = getPayHereRequest(paymentRequest);            PayHereModel payHereModel = new PayHereModel();            BeanUtils.copyProperties(request, payHereModel);            payHereRepository.save(payHereModel);            return generatePayHereUrl(request);        } catch (Exception e) {            // Catch and rethrow any exception with a meaningful message for debugging            throw new RuntimeException("Error occurred during payment processing", e);        }    }    /**     * Constructs a PayHereRequest object from the given PaymentRequest.     * This method also generates a secure hash required for PayHere payments.     *     * @param paymentRequest The payment request containing payment details.     * @return A fully populated PayHereRequest object.     * @throws NoSuchAlgorithmException If the MD5 algorithm is not available (rare case).     */    private PayHereRequest getPayHereRequest(PaymentRequest paymentRequest) throws NoSuchAlgorithmException {        // Generate a hash string for payment security        String hash = merchantId + paymentRequest.getOrderId() + PayHereUtils.formatAmount(paymentRequest.getAmount())+ paymentRequest.getCurrency()+ PayHereUtils.getMd5(merchantSecret).toUpperCase();        // Create and populate the PayHereRequest object        PayHereRequest request = new PayHereRequest();        request.setMerchant_id(merchantId);        request.setReturn_url(successUrl);        request.setCancel_url(cancelUrl + "menu/payment/:name/"+paymentRequest.getOrderId());        request.setNotify_url(notifyUrl);        request.setAmount(paymentRequest.getAmount());        request.setCurrency(paymentRequest.getCurrency());        request.setOrder_id(paymentRequest.getOrderId());        request.setUserId(paymentRequest.getUserId());        request.setFirst_name(paymentRequest.getFirstName());        request.setLast_name(paymentRequest.getLastName());        request.setEmail(paymentRequest.getEmail());        request.setBookingId(paymentRequest.getBookingId());        request.setPhone(paymentRequest.getPhone());        request.setCity(paymentRequest.getCity());        request.setCountry(paymentRequest.getCountry());        request.setAddress(paymentRequest.getAddress());        request.setHash(PayHereUtils.getMd5(hash).toUpperCase());        return request;    }    /**     * Generates the PayHere payment URL by appending the required parameters to the base URL.     * This URL is used to redirect the user to PayHere for payment processing.     *     * @param request The PayHereRequest object containing payment details.     * @return A fully constructed PayHere payment URL.     */    private String generatePayHereUrl(PayHereRequest request) {        return payHereUrl + "?" +                "merchant_id=" + request.getMerchant_id() +                "&return_url=" + request.getReturn_url() +                "&cancel_url=" + request.getCancel_url() +                "&notify_url=" + request.getNotify_url() +                "&order_id=" + request.getOrder_id() +                "&items=" + "Booking_" +(request.getBookingId().toString())+                "&currency=" + request.getCurrency() +                "&amount=" + request.getAmount() +                "&first_name=" + request.getFirst_name() +                "&last_name=" + request.getLast_name() +                "&email=" + request.getEmail() +                "&phone=" + request.getPhone() +                "&address=" + request.getAddress() +                "&city=" + request.getCity() +                "&country=" + request.getCountry()                + "&hash=" + request.getHash();    }}