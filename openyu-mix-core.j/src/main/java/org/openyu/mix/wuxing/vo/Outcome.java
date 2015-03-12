package org.openyu.mix.wuxing.vo;

import java.util.List;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.openyu.mix.app.vo.Prize;
import org.openyu.commons.bean.IdBean;
import org.openyu.commons.enumz.IntEnum;
import com.sun.xml.bind.AnyTypeAdapter;

/**
 * 開出的結果
 */
@XmlJavaTypeAdapter(AnyTypeAdapter.class)
public interface Outcome extends IdBean
{
	String KEY = Outcome.class.getName();

	/**
	 * 結果類別,勝敗生和
	 * 
	 */
	public enum OutcomeType implements IntEnum
	{
		/**
		 * 勝
		 */
		WIN(1),

		/**
		 * 敗
		 */
		LOSE(2),

		/**
		 * 生
		 */
		BIRTH(3),

		/**
		 * 和
		 */
		TIE(4),

		//
		;

		private final int value;

		private OutcomeType(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}
	}

	/**
	 * banker id
	 * 
	 * @return
	 */
	String getBankerId();

	void setBankerId(String bankerId);

	/**
	 * player id
	 * 
	 * @return
	 */
	String getPlayerId();

	void setPlayerId(String playerId);

	/**
	 * 開出的獎
	 * 
	 * @return
	 */
	List<Prize> getPrizes();

	void setPrizes(List<Prize> prizes);

}
