package ir.mghhrn.atm.bank.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequestDto {

    private String cardNumber;
    private String atmSerialNumber;
    private String authenticationMode;
    private String pinCode;
    private String fingerprint;
}
