package ir.mghhrn.atm.bank.hystrix;

import ir.mghhrn.atm.bank.BankServiceClient;
import ir.mghhrn.atm.bank.dto.*;
import org.springframework.stereotype.Component;

@Component
public class BankServiceFallback implements BankServiceClient {

    @Override
    public CardVerificationResponseDto verifyCard(CardVerificationRequestDto requestDto) {
        return null;
    }

    @Override
    public AuthenticationResponseDto authenticateCard(AuthenticationRequestDto requestDto) {
        return null;
    }

    @Override
    public void closeSession(String authenticationToken) {
    }

    @Override
    public CheckBalanceResponseDto getBalance(String authenticationToken) {
        return null;
    }

    @Override
    public AccountTransactionResponseDto withdrawMoney(String authenticationToken, WithdrawRequestDto requestDto) {
        return null;
    }

    @Override
    public AccountTransactionResponseDto depositMoney(String authenticationToken, DepositRequestDto requestDto) {
        return null;
    }

    @Override
    public void changeAuthenticationMode(String authenticationToken, AuthenticationModeRequestDto requestDto) {

    }
}
