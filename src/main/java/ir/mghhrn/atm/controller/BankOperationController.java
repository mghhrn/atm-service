package ir.mghhrn.atm.controller;

import ir.mghhrn.atm.bank.BankServiceClient;
import ir.mghhrn.atm.bank.dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-operation")
public class BankOperationController {

    private static final Logger logger = LogManager.getLogger(BankOperationController.class);
    private static final String AUTHENTICATION_TOKEN_HEADER = "AUTHENTICATION_TOKEN";

    private final BankServiceClient bankServiceClient;

    public BankOperationController(@Qualifier("ir.mghhrn.atm.bank.BankServiceClient") BankServiceClient bankServiceClient) {
        this.bankServiceClient = bankServiceClient;
    }

    @PostMapping("/verification")
    public ResponseEntity<CardVerificationResponseDto> verifyCard(
            @RequestBody CardVerificationRequestDto requestDto) {
        CardVerificationResponseDto responseDto = bankServiceClient.verifyCard(requestDto);
        logger.info("successfully verified card {}", requestDto.getCardNumber());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDto> authenticateCard(
            @RequestBody AuthenticationRequestDto requestDto) {
        AuthenticationResponseDto responseDto = bankServiceClient.authenticateCard(requestDto);
        logger.info("successfully authenticated card {}", requestDto.getCardNumber());
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/close-session")
    public ResponseEntity<Void> closeSession(
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        bankServiceClient.closeSession(authenticationToken);
        logger.info("successfully closed session for token {}", authenticationToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account/check-balance")
    public ResponseEntity<CheckBalanceResponseDto> getBalance(
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        CheckBalanceResponseDto responseDto = bankServiceClient.getBalance(authenticationToken);
        logger.info("successfully got balance");
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/account/withdraw")
    public ResponseEntity<AccountTransactionResponseDto> withdrawMoney(
            @RequestBody WithdrawRequestDto requestDto,
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        AccountTransactionResponseDto responseDto = bankServiceClient.withdrawMoney(authenticationToken, requestDto);
        logger.info("successfully withdrew amount of {} from account {}", requestDto.getAmount(), responseDto.getAccountId());
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/account/deposit")
    public ResponseEntity<AccountTransactionResponseDto> depositMoney(
            @RequestBody DepositRequestDto requestDto,
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        AccountTransactionResponseDto responseDto = bankServiceClient.depositMoney(authenticationToken, requestDto);
        logger.info("successfully deposited amount of {} to account {}", requestDto.getDepositAmount(), responseDto.getAccountId());
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/authentication-mode")
    public ResponseEntity<Void> changeAuthenticationMode(
            @RequestBody AuthenticationModeRequestDto requestDto,
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        bankServiceClient.changeAuthenticationMode(authenticationToken, requestDto);
        logger.info("successfully changed authentication mode");
        return ResponseEntity.ok().build();
    }
}
