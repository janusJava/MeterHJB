package com.seafta.meterhj;

import com.seafta.meterhj.domain.dto.account.AccountSnapshot;
import com.seafta.meterhj.domain.dto.meter.MeterSnapshot;
import com.seafta.meterhj.domain.persistence.model.account.Account;
import com.seafta.meterhj.domain.persistence.model.enums.MeterType;
import com.seafta.meterhj.domain.request.account.AccountCreateRequest;
import com.seafta.meterhj.domain.request.meter.MeterCreateRequest;
import com.seafta.meterhj.domain.service.account.AccountService;
import com.seafta.meterhj.domain.service.meter.MeterService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MeterHjApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeterHjApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(AccountService accountService, MeterService meterService) {

		return args -> {

			AccountSnapshot account = accountService.createAccount(AccountCreateRequest.builder()
					.email("test1234@gmail.com")
					.password("password")
					.fullName("Pan Pan")
					.build());
			MeterSnapshot meter = meterService.createMeter(MeterCreateRequest.builder()
					.name("Example Name")
					.type(MeterType.G4).build(), account.getAccountId());
			MeterSnapshot meterDwa = meterService.createMeter(MeterCreateRequest.builder()
					.name("Example |2| Name")
					.type(MeterType.G6).build(), account.getAccountId());
			MeterSnapshot meterTrzy = meterService.createMeter(MeterCreateRequest.builder()
					.name("Example |3| Name")
					.type(MeterType.G6).build(), account.getAccountId());
			System.out.println(meter.getMeterId() + " Tu jest ID meter");
			System.out.println(meterDwa.getMeterId() + " Tu jest ID meter");
			System.out.println(account.getAccountId() + "Tu jest ID account");
		};
	}

}
