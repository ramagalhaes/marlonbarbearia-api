package br.com.marlonbarbearia.filters;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfigs {

    @Bean
    public FilterRegistrationBean<ConfirmedAccountFilter> confirmedAccountFilter() {
        FilterRegistrationBean<ConfirmedAccountFilter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new ConfirmedAccountFilter());
        filter.addUrlPatterns("/api/v1/customers/*");
        filter.setOrder(1);
        return filter;
    }

}
