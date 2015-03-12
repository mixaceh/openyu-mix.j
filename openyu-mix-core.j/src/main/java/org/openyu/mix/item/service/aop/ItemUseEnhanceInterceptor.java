package org.openyu.mix.item.service.aop;

import java.lang.reflect.Method;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.openyu.mix.app.service.aop.supporter.AppMethodInterceptorSupporter;
import org.openyu.mix.item.service.ItemLogService;
import org.openyu.mix.item.service.ItemService;
import org.openyu.mix.item.service.ItemService.ErrorType;
import org.openyu.mix.item.service.ItemService.ActionType;
import org.openyu.mix.item.vo.Item;
import org.openyu.mix.role.vo.Role;
import org.openyu.commons.lang.ClassHelper;

/**
 * 消耗強化道具,強化道具攔截器
 */
public class ItemUseEnhanceInterceptor extends AppMethodInterceptorSupporter {

	private static transient final Logger LOGGER = LoggerFactory
			.getLogger(ItemUseEnhanceInterceptor.class);

	@Autowired
	@Qualifier("itemLogService")
	protected transient ItemLogService itemLogService;

	/**
	 * 使用強化防具道具
	 * 
	 * ItemService
	 * 
	 * ErrorType useEnhanceArmorThing(boolean sendable, String roleId, String
	 * targetId, Item item);
	 */
	private static final Method useEnhanceArmorThing = ClassHelper
			.getDeclaredMethod(ItemService.class, "useEnhanceArmorThing",
					new Class[] { boolean.class, String.class, String.class,
							Item.class });

	/**
	 * 使用強化武器道具
	 * 
	 * ItemService
	 * 
	 * ErrorType useEnhanceWeaponThing(boolean sendable, String roleId, String
	 * targetId, Item item);
	 */
	private static final Method useEnhanceWeaponThing = ClassHelper
			.getDeclaredMethod(ItemService.class, "useEnhanceWeaponThing",
					new Class[] { boolean.class, String.class, String.class,
							Item.class });

	/**
	 * 使用強化土地道具
	 * 
	 * ItemService
	 * 
	 * ErrorType useEnhanceLandThing(boolean sendable, String roleId, String
	 * targetId, Item item);
	 */
	private static final Method useEnhanceLandThing = ClassHelper
			.getDeclaredMethod(ItemService.class, "useEnhanceLandThing",
					new Class[] { boolean.class, String.class, String.class,
							Item.class });

	private static final Method getItem = ClassHelper.getDeclaredMethod(
			ItemService.class, "getItem", new Class[] { Role.class,
					String.class });

	public ItemUseEnhanceInterceptor() {
	}

	public Object invoke(MethodInvocation methodInvocation, Method method,
			Class<?>[] paramTypes, Object[] args) {
		// 傳回值
		Object result = null;
		try {
			// 取得被代理的service
			ItemService itemService = (ItemService) methodInvocation.getThis();

			// --------------------------------------------------
			// proceed前
			// --------------------------------------------------
			// 參數
			boolean sendable = (Boolean) args[0];
			Role role = (Role) args[1];
			String targetId = (String) args[2];
			Item item = (Item) args[3];// 消耗的道具
			// 原道具
			Item origItem = itemService.getItem(role, targetId);
			// 強化前的道具
			Item beforeItem = origItem.clone(origItem);

			// --------------------------------------------------
			result = methodInvocation.proceed();
			// --------------------------------------------------

			// --------------------------------------------------
			// proceed後
			// --------------------------------------------------

			if (method.equals(useEnhanceArmorThing)
					|| method.equals(useEnhanceWeaponThing)
					|| method.equals(useEnhanceLandThing)) {
				// 傳回值
				ErrorType ret = (ErrorType) result;
				//
				if (ret == ErrorType.NO_ERROR) {
					itemLogService.recordChangeEnhance(role,
							ActionType.USE_ENHANCE, beforeItem, origItem, item);
				}
			} else {
				LOGGER.error(method.getName() + " not matched to record");
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
		}
		return result;
	}
}
