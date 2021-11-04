package ir.mghhrn.atm.controller;

import ir.mghhrn.atm.bank.BankServiceClient;
import ir.mghhrn.atm.bank.dto.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bank-operation")
public class BankOperationController {

    private static final String AUTHENTICATION_TOKEN_HEADER = "AUTHENTICATION_TOKEN";

    private final BankServiceClient bankServiceClient;

    public BankOperationController(@Qualifier("ir.mghhrn.atm.bank.BankServiceClient") BankServiceClient bankServiceClient) {
        this.bankServiceClient = bankServiceClient;
    }

    @PostMapping("/verification")
    public ResponseEntity<CardVerificationResponseDto> verifyCard(
            @RequestBody CardVerificationRequestDto requestDto) {
        CardVerificationResponseDto responseDto = bankServiceClient.verifyCard(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthenticationResponseDto> authenticateCard(
            @RequestBody AuthenticationRequestDto requestDto) {
        AuthenticationResponseDto responseDto = bankServiceClient.authenticateCard(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/close-session")
    public ResponseEntity<Void> closeSession(
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        bankServiceClient.closeSession(authenticationToken);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account/check-balance")
    public ResponseEntity<CheckBalanceResponseDto> getBalance(
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        CheckBalanceResponseDto responseDto = bankServiceClient.getBalance(authenticationToken);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/account/withdraw")
    public ResponseEntity<AccountTransactionResponseDto> withdrawMoney(
            @RequestBody WithdrawRequestDto requestDto,
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        AccountTransactionResponseDto responseDto = bankServiceClient.withdrawMoney(authenticationToken, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/account/deposit")
    public ResponseEntity<AccountTransactionResponseDto> depositMoney(
            @RequestBody DepositRequestDto requestDto,
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        AccountTransactionResponseDto responseDto = bankServiceClient.depositMoney(authenticationToken, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/authentication-mode")
    public ResponseEntity<Void> changeAuthenticationMode(
            @RequestBody AuthenticationModeRequestDto requestDto,
            @RequestHeader(AUTHENTICATION_TOKEN_HEADER) String authenticationToken) {
        bankServiceClient.changeAuthenticationMode(authenticationToken, requestDto);
        return ResponseEntity.ok().build();
    }
}
