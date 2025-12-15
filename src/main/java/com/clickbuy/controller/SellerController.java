package com.clickbuy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clickbuy.Exception.SellerException;
import com.clickbuy.domain.AccountStatus;

import com.clickbuy.model.Seller;
import com.clickbuy.model.SellerReport;
import com.clickbuy.model.VerificationCode;
import com.clickbuy.repository.VerificationCodeRepository;
import com.clickbuy.request.LoginRequest;
import com.clickbuy.response.AuthResponse;
import com.clickbuy.service.AuthService;
import com.clickbuy.service.EmailService;
import com.clickbuy.service.SellerReportService;
import com.clickbuy.service.SellerService;
import com.clickbuy.service.impl.SellerServiceImpl;
import com.clickbuy.utils.OtpUtils;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/sellers")
public class SellerController {

	private final SellerService sellerService;
	private final VerificationCodeRepository verificationCodeRepository;
	private AuthService authService;
	private final EmailService emailService;
	private final SellerReportService sellerReportService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginSeller(@RequestBody LoginRequest req) throws Exception {

		String otp = req.getOtp();
		String email = req.getEmail();

		req.setEmail("seller_" + email);
		AuthResponse authResponse = authService.signing(req);
	

		return ResponseEntity.ok(authResponse);
	}

	@PatchMapping("/verify/{otp}")
	public ResponseEntity<Seller> verifySellermail(@PathVariable String otp) throws Exception {
		VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

		if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
			throw new Exception("Wrong otp..");
		}

		Seller seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);
		return new ResponseEntity<>(seller, HttpStatus.OK);
	}

	@PostMapping 
	public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception, MessagingException {

		Seller savedSeller = sellerService.createSeller(seller);

		String otp = OtpUtils.generateOtp();

		VerificationCode verificationCode = new VerificationCode();
		verificationCode.setOtp(otp);
		verificationCode.setEmail(seller.getEmail());
		verificationCodeRepository.save(verificationCode);

		String subject = "Nexamart email verification code: ";
		String text = "Welcome to nexamart, verify your account using this link ";
		String frontend_url = "http://localhost:3000/verify-seller/";

		emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject,
				text + frontend_url);
		return new ResponseEntity<>(savedSeller, HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerException {
		Seller seller = sellerService.getSellerById(id);
		return new ResponseEntity<Seller>(seller, HttpStatus.OK);
	}

	@GetMapping("/profile")
	public ResponseEntity<Seller> getSellerByJwt(@RequestHeader("Authorization") String jwt) throws Exception {
		Seller sellerProfile = sellerService.getSellerProfile(jwt);
		return new ResponseEntity<Seller>(sellerProfile, HttpStatus.OK);
	}
	
	@GetMapping("/report")
	public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception{
		
		Seller seller = sellerService.getSellerProfile(jwt);
		SellerReport sellerReport = sellerReportService.getSellerReport(seller);
		return new ResponseEntity<>(sellerReport,HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus status) {
		List<Seller> allSellers = sellerService.getAllSellers(status);

		return ResponseEntity.ok(allSellers);
	}
	
    @PatchMapping
	public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt, @RequestBody Seller seller)
			throws Exception {
		Seller sellerProfile = sellerService.getSellerProfile(jwt);
		Seller updateSeller = sellerService.updateSeller(sellerProfile.getId(),seller);
		return ResponseEntity.ok(updateSeller);

	}
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception{
    	sellerService.deleteSeller(id);
    	return ResponseEntity.noContent().build();
    	
    }
	
	

}