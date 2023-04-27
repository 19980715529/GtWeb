/**
 * Copyright (c) 2015-2017, Chill Zhuang 庄骞 (smallchill@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smallchill.db.config.meta.intercept;

import com.smallchill.core.aop.Invocation;
import com.smallchill.core.intercept.BladeValidator;

public class RechargeActivityValidator extends BladeValidator {

	@Override
	protected void doValidate(Invocation inv) {
		validateRequired("rechargeactivity.begintime", "请输入充值活动开启时间!");
		validateRequired("rechargeactivity.endtime", "请输入充值活动结束时间!");
		validateRequired("rechargeactivity.extragold", "请输入额外赠送金币!");
		validateRequired("rechargeactivity.extraticket", "请输入额外赠送奖券!");
		validateRequired("rechargeactivity.buytimes", "请输入活动期间可购买次数!");
	}
}
