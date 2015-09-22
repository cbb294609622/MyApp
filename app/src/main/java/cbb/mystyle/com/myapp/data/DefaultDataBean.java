package cbb.mystyle.com.myapp.data;

import java.util.ArrayList;
import java.util.List;

import cbb.mystyle.com.myapp.R;

public class DefaultDataBean {
	/**
	 * 左侧数据
	 */
	private static List<String> leftItem;
	/**
	 * 主页数据
	 */
	private static List<String> homeItem;
	/**
	 * popuwindow图片
	 */
	public static int[] imgPic = {R.mipmap.img_popu_phone,
			R.mipmap.img_popu_call,
			R.mipmap.img_popu_group,
			R.mipmap.img_popu_qrcode,
			R.mipmap.img_popu_computer};
	/**
	 * popuwindow文字
	 */
	public static String[] imgName = {"手机互传", "爱信电话", "新建群组", "扫二维码", "电脑共享"};

	/**
	 * 左侧菜单栏的数据填充
	 * @return 返回的数据
	 */
	public static List<String> leftItemData() {
		leftItem = new ArrayList<String>();
		leftItem.add("我的首页");
		leftItem.add("个人中心");
		leftItem.add("图片浏览");
		leftItem.add("亮点行程");
		leftItem.add("期待加入");
		leftItem.add("关于我们");
		leftItem.add("设置中心");
		return leftItem;
	}
	/**
	 * home界面的数据展示
	 * @return
	 */
	public static List<String> homeItemData(){
		homeItem = new ArrayList<String>();
		homeItem.add("宋江");
		homeItem.add("卢义");
		homeItem.add("吴用");
		homeItem.add("龚旺");
		homeItem.add("丁孙");
		homeItem.add("穆春");
		homeItem.add("曹正");
		homeItem.add("宋万");
		homeItem.add("杜迁");
		homeItem.add("蔡庆");
		homeItem.add("李立");
		homeItem.add("李忠");
		homeItem.add("李立");
		homeItem.add("乐和");
		homeItem.add("汤隆");
		homeItem.add("宋江");
		homeItem.add("卢义");
		homeItem.add("吴用");
		homeItem.add("龚旺");
		homeItem.add("丁孙");
		homeItem.add("穆春");
		homeItem.add("曹正");
		homeItem.add("宋万");
		return homeItem;
	}
}
