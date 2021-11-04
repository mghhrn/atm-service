package ir.mghhrn.atm.bank;

import ir.mghhrn.atm.bank.config.BankServiceClientConfiguration;
import ir.mghhrn.atm.bank.dto.*;
import ir.mghhrn.atm.bank.hystrix.BankServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "bankservice-client",
        qualifiers = {"bank-service-client"},
        url = "${bank.service.url}",
        configuration = BankServiceClientConfiguration.class,
        fallback = BankServiceFallback.class)
public interface BankServiceClient {

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/card/verification", produces = "application/json")
    CardVerificationResponseDto verifyCard(@RequestBody CardVerificationRequestDto requestDto);

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/card/authentication", produces = "application/json")
    AuthenticationResponseDto authenticateCard(@RequestBody AuthenticationRequestDto requestDto);

    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/card/close-session", produces = "application/json")
    void closeSession(@RequestHeader("AUTHENTICATION_TOKEN") String authenticationToken);

    @RequestMapping(method = RequestMethod.GET, value = "/api/v1/card/account/check-balance", produces = "application/json")
    CheckBalanceResponseDto getBalance(@RequestHeader("AUTHENTICATION_TOKEN") String authenticationToken);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/card/account/withdraw", produces = "application/json")
    AccountTransactionResponseDto withdrawMoney(@RequestHeader("AUTHENTICATION_TOKEN") String authenticationToken,
                                       @RequestBody WithdrawRequestDto requestDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/card/account/deposit", produces = "application/json")
    AccountTransactionResponseDto depositMoney(@RequestHeader("AUTHENTICATION_TOKEN") String authenticationToken,
                                             @RequestBody DepositRequestDto requestDto);

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/card/authentication-mode", produces = "application/json")
    void changeAuthenticationMode(@RequestHeader("AUTHENTICATION_TOKEN") String authenticationToken,
                                               @RequestBody AuthenticationModeRequestDto requestDto);
}
