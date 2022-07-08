package com.icptechno.admincore.auth;

import com.icptechno.admincore.auth.dto.*;
import com.icptechno.admincore.common.ResponseBuilder;
import com.icptechno.admincore.common.ResponseHolder;
import com.icptechno.admincore.common.security.AppUserDetail;
import com.icptechno.admincore.user.ApplicationUser;
import com.icptechno.admincore.user.UserService;
import com.icptechno.admincore.user.dto.UserConverter;
import com.icptechno.admincore.user.dto.UserCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final TokenService tokenService;

    private final UserService userService;

    private final UserConverter userConverter;

    public AuthController(TokenService tokenService, UserService userService, UserConverter userConverter) {
        this.tokenService = tokenService;
        this.userService = userService;
        this.userConverter = userConverter;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseHolder<Long>> createUser(@RequestBody UserCreateDto userCreateDto) {
        ApplicationUser user = userConverter.convertToEntity(userCreateDto);
        // create user
        user = userService.create(user);
        // save user
        return ResponseBuilder.createdResponseBuilder(user.getId()).build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseHolder<RefreshTokenDto>> refresh(@RequestBody RefreshTokenDto refreshTokenDto) throws Exception {

        String refreshToken = refreshTokenDto.getRefreshToken();
        String accessToken = refreshTokenDto.getAccessToken();

        logger.debug(String.format("%s %s", refreshToken, accessToken));
        Long id = tokenService.getUserIdFromAccessToken(accessToken);

        if (!tokenService.isRefreshTokenValid(id, refreshToken)) {
            throw new Exception("Invalid Refresh Token");
        }

        tokenService.removeRefreshToken(id, refreshToken);

        String newAccessToken = tokenService.createAccessToken(id);
        String newRefreshToken = tokenService.createRefreshToken(id);

        RefreshTokenDto newTokens = new RefreshTokenDto();
        newTokens.setAccessToken(newAccessToken);
        newTokens.setRefreshToken(newRefreshToken);

        return ResponseBuilder.okResponseBuilder(newTokens).build();
    }

    @PostMapping("/password/request")
    public ResponseEntity<ResponseHolder<Object>> requestPasswordReset(@RequestBody RequestPasswordResetDto resetDto) {
        String email = resetDto.getEmail();

        Long id = userService.getOneByEmail(email).getId();

        String token = tokenService.createPasswordResetToken(id);

        // send user the token
        logger.debug(String.format("Password Reset Token: %s", token));

        return ResponseBuilder.okResponseBuilder(null).build();
    }

    @PostMapping("/password/verify")
    public ResponseEntity<ResponseHolder<Object>> verifyPasswordResetCode(@RequestBody VerifyPasswordResetDto verifyPasswordResetDto) throws Exception {
        String email = verifyPasswordResetDto.getEmail();
        String resetToken = verifyPasswordResetDto.getToken();

        Long id = userService.getOneByEmail(email).getId();

        if (!tokenService.isPasswordResetTokenValid(id, resetToken)) {
            throw new Exception("Invalid Password Reset Token");
        }

        return ResponseBuilder.okResponseBuilder(null).build();
    }

    @PostMapping("/password/reset")
    public ResponseEntity<ResponseHolder<Object>> passwordReset(@RequestBody PasswordResetDto passwordResetDto) throws Exception {
        String email = passwordResetDto.getEmail();
        String resetToken = passwordResetDto.getToken();
        Long id = userService.getOneByEmail(email).getId();
        if (!tokenService.isPasswordResetTokenValid(id, resetToken)) {
            throw new Exception("Invalid Password Reset Token");
        }
        tokenService.removePasswordResetToken(id, resetToken);
        String password = passwordResetDto.getPassword();
        userService.updatePasswordById(id, password);
        return ResponseBuilder.okResponseBuilder(null).build();
    }

    @PostMapping("/password/change")
    public ResponseEntity<ResponseHolder<Boolean>> passwordChange(@RequestBody PasswordChangeDto passwordChangeDto) {
        String newPassword = passwordChangeDto.getPassword();
        AppUserDetail userDetail = (AppUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.updatePasswordById(userDetail.getId(), newPassword);
        return ResponseBuilder.okResponseBuilder(true).build();
    }

}
