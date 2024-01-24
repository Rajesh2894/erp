//
// package com.abm.mainet.common.util;
//
// import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_ARGUMENTS;
// import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_INVOCATION_TIME;
// import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_METHOD_NAME;
// import static org.springframework.aop.interceptor.CustomizableTraceInterceptor.PLACEHOLDER_TARGET_CLASS_NAME;
//
// import javax.persistence.EntityManagerFactory;
//
// import org.springframework.aop.Advisor;
// import org.springframework.aop.aspectj.AspectJExpressionPointcut;
// import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
// import org.springframework.aop.interceptor.JamonPerformanceMonitorInterceptor;
// import org.springframework.aop.support.DefaultPointcutAdvisor;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.context.annotation.EnableAspectJAutoProxy;
// import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
// import org.springframework.web.filter.CommonsRequestLoggingFilter;
//
// @Configuration
// @EnableAspectJAutoProxy(proxyTargetClass = true)
// public class SpringLoggingUtil {
// @Bean
// public CommonsRequestLoggingFilter requestLoggingFilter() {
// CommonsRequestLoggingFilter crlf = new CommonsRequestLoggingFilter();
// crlf.setIncludeClientInfo(true);
// crlf.setIncludeQueryString(true);
// crlf.setIncludePayload(true);
// return crlf;
// }
//
// @Bean
// public CustomizableTraceInterceptor customizableTraceInterceptor() {
// CustomizableTraceInterceptor cti = new CustomizableTraceInterceptor();
// cti.setEnterMessage("Entering method '" + PLACEHOLDER_METHOD_NAME + "(" +
// PLACEHOLDER_ARGUMENTS + ")' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "]");
// cti.setExitMessage("Exiting method '" +
// PLACEHOLDER_METHOD_NAME + "' of class [" + PLACEHOLDER_TARGET_CLASS_NAME + "] took " + PLACEHOLDER_INVOCATION_TIME
// + "ms.");
// return cti;
// }
//
// @Bean
// public Advisor traceAdvisor() {
// AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
// pointcut.setExpression("execution(public com.abm...(..))");
// return new DefaultPointcutAdvisor(pointcut,
// customizableTraceInterceptor());
// }
//
// @Bean
// public JamonPerformanceMonitorInterceptor jamonPerformanceMonitorInterceptor() {
// return new JamonPerformanceMonitorInterceptor();
// }
//
// @Bean
// public Advisor performanceAdvisor() {
// AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
// pointcut.setExpression("execution(public biz.deinum...(..))");
// return new DefaultPointcutAdvisor(pointcut,
// jamonPerformanceMonitorInterceptor());
// }
//
// @Bean
// public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
// HibernateJpaSessionFactoryBean jssfb = new HibernateJpaSessionFactoryBean();
// jssfb.setEntityManagerFactory(emf);
// return jssfb;
// }
// }
