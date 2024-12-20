package com.mediamate.controller.dashboard.sms_api;

import com.mediamate.model.renter.Renter;
import com.mediamate.model.renter.RenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.smsapi.OAuthClient;
import pl.smsapi.api.SmsFactory;
import pl.smsapi.api.action.sms.SMSSend;
import pl.smsapi.exception.ClientException;
import pl.smsapi.exception.SmsapiException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

@Service
public class SmsSenderService {

    private final SmsFactory smsFactory;
    private final String PHONE_REGEX = "^\\+?[0-9]{9,15}$";

    private final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);
    private final RenterService renterService;
    @Autowired
    public SmsSenderService(@Value("${SMS_API_TOKEN}")String token, RenterService renterService) {
        try {
            OAuthClient oAuthClient = new OAuthClient(token);
            this.smsFactory = new SmsFactory(oAuthClient);
        }
        catch (ClientException e){
            throw new RuntimeException("Failure while creating SmsApiService", e);
        }
        this.renterService = renterService;
    }


       public void sendSms (Renter renter,String message,String numberPhone){
        try {
            SMSSend actionSmsSend = this.smsFactory.actionSend(numberPhone, message);
            actionSmsSend.execute();
            renterService.updateSmsDateSent(renter);
        }catch (SmsapiException e){
            throw new RuntimeException("Failure while sending Sms", e);
        }
        }

        public String generateSmsMessage(BigDecimal totalMediaCost){
        return "Cześć! Rozliczenie na ten miesiąc wynosi " + totalMediaCost +  " zł. Miłego dnia!";
        }

        private boolean isValidPhoneNumber(String phoneNumber) {
            String sanitizedPhoneNumber = phoneNumber.replaceAll("\\s+", "");
            return PHONE_PATTERN.matcher(sanitizedPhoneNumber).matches();
        }
        public void sendSmsIfValid (Renter renter,String phoneNumber,BigDecimal totalCost ){
        if(isMonthSinceLastSms(renter)){
        if(isValidPhoneNumber(phoneNumber)) {
            String message = generateSmsMessage(totalCost);
            sendSms(renter,message,phoneNumber);
        }
        }
        }
    public boolean isMonthSinceLastSms (Renter renter){
        if(renter.getSmsSentDate()==null){
            return true;
        }
        LocalDate today = LocalDate.now();
        LocalDate lastSmsDate = renter.getSmsSentDate();
        LocalDate oneMonthAgo = today.minusMonths(1);
        return (lastSmsDate.isBefore(oneMonthAgo)||lastSmsDate.isEqual(oneMonthAgo));
    }
    }

