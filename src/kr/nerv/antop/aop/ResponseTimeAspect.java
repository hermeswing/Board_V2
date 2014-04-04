package kr.nerv.antop.aop;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseTimeAspect {
	private final Logger logger = Logger.getLogger(getClass());

	@Around("execution(public * kr.nerv.antop.servlet..*(..))")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		// 컨트롤러의 요청부터 응답까지의 시간 구하기
		String signatureString = joinPoint.getSignature().toShortString();
		long start = System.currentTimeMillis();

		try {
			// 실행
			return joinPoint.proceed();
		} finally {
			if (logger.isDebugEnabled()) {
				long finish = System.currentTimeMillis();
				logger.debug(signatureString + " " + (finish - start) + "ms");
			}

		}

	}

}
