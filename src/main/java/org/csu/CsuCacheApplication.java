package org.csu;

import org.csu.filter.HystrixRequestContextFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.FilterRegistration;

@SpringBootApplication
public class CsuCacheApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsuCacheApplication.class, args);
	}

	/**
	 * 缓存失效了，然后重新批量获取商品的详情接口，但是传递的是1,2,3,1这样的重复数据
	 * @return
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationbean = new FilterRegistrationBean(
				new HystrixRequestContextFilter());
		filterRegistrationbean.addUrlPatterns("/");
		return filterRegistrationbean;
	}
}
