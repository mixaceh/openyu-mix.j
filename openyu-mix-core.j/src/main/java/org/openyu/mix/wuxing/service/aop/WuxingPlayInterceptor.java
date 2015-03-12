package org.openyu.mix.wuxing.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.role.vo.Role;
import org.openyu.mix.wuxing.service.WuxingService.PlayResult;
import org.openyu.mix.wuxing.service.WuxingLogService;

/**
 * 五行玩的攔截器
 */
public class WuxingPlayInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(WuxingPlayInterceptor.class);

	@Autowired
	@Qualifier("wuxingLogService")
	protected transient WuxingLogService wuxingLogService;

	public WuxingPlayInterceptor() {
	}

	/**
	 * WuxingService
	 * 
	 * PlayResult play(boolean sendable, Role role, int playValue)
	 */
	public Object invoke(MethodInvocation methodInvocation, Method method,
			Class<?>[] paramTypes, Object[] args) {
		// 傳回值
		Object result = null;
		try {
			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			int playValue = (Integer) args[2];

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			// 傳回值
			PlayResult ret = (PlayResult) result;
			//
			if (ret != null) {
				// 記錄玩的
				wuxingLogService.recordPlay(role, ret.getPlayType(),
						ret.getPlayTime(), ret.getOutcome(),
						ret.getTotalTimes(), ret.getSpendGold(),
						ret.getSpendItems(), ret.getSpendCoin());

				// 記錄開出的結果,有成名的
				wuxingLogService.recordFamous(role, ret.getPlayType(),
						ret.getPlayTime(), ret.getOutcomes());
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}

}
