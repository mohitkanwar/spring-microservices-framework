package com.mk.calculatorsservice.controller;


import com.mk.calculatorsservice.domain.request.FDRequest;
import com.mk.calculatorsservice.domain.response.FDResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculator")
public class FDController {

    /**
     * A method that is not supposed to be exposed publicly.
     * However the control is not with this API, it lies on the gateway.
     * @param fdRequest
     * @return
     */
    @PostMapping("/fd/interest")
    public FDResponse calculateFD(@RequestBody FDRequest fdRequest) {
        // Example interest rates for different schemes
        FDResponse fdResponse = getFdResponse(fdRequest);

        return fdResponse;
    }

    /**
     * Same as the previous method, just that this is exposed publicly for demo purposes.
     * @param fdRequest
     * @return
     */
    @PostMapping("/fd/interest/p")
    public FDResponse calculatePublicFD(@RequestBody FDRequest fdRequest) {

        FDResponse fdResponse = getFdResponse(fdRequest);

        return fdResponse;
    }

    private static FDResponse getFdResponse(FDRequest fdRequest) {
        double interestRate = switch (fdRequest.schemeCode()) {
            case "SCHEME_A" -> 5.0;
            case "SCHEME_B" -> 6.0;
            case "SCHEME_C" -> 7.0;
            default -> throw new IllegalArgumentException("Invalid scheme code");
        };

        double interest = (fdRequest.amount() * interestRate * fdRequest.duration()) / 100;
        double maturityAmount = fdRequest.amount() + interest;

        return new FDResponse(interestRate, maturityAmount);
    }
}
