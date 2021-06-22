package br.gov.sp.fatec.domain.view;

public class View {

	public static interface User {
	
		public static interface Create {}
		
		public static interface Detail extends Create {}
		
		public static interface Update {}
	}
	
	public static interface Post {
		
		public static interface Create {}
		
		public static interface Detail extends Create {}
		
		public static interface CreateWithFile extends Create {}
		
		public static interface Update {}
		
		public static interface Info extends Detail {}
	}
	
	public static interface File {
		
		public static interface Create {}
		
		public static interface Detail extends Create {}
	}
	
	public static interface Answer {
		
		public static interface Create {}
		
		public static interface Detail extends Create {}
		
		public static interface Update {}
	}
}
