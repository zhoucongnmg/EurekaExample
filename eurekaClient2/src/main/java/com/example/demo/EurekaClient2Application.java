package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClient2Application {

	private static final String ERK_A = "eurekaA";

	@Autowired
	private DiscoveryClient discoveryClient;

	public static void main(String[] args) {
		SpringApplication.run(EurekaClient2Application.class, args);
	}

	@RequestMapping("/")
	public String method(){
		List<ServiceInstance> list = discoveryClient.getInstances(ERK_A);
		String eurekaAResult = null;
		if (list != null && list.size() > 0 ) {
			URI uri = list.get(0).getUri();
			if(uri != null){
				eurekaAResult = (new RestTemplate()).getForObject(uri,String.class);
			}
		}
		return eurekaAResult + "  eurekaB";
	}
	@RequestMapping("/detail")
	public List<ServiceInstance> detail(){
		return discoveryClient.getInstances(ERK_A);
	}

}
