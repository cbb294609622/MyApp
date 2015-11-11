package cbb.mystyle.com.myapp.bean;

import java.util.List;


public class HomeBean{
	public String status;
	public String message;
	public Data data;
	
	public class Data{
		public List<Goods> goods;
		
		public class Goods{
			public String id;
			public String title;
			public String read;
			public String photo;
		}
		
	}
}
