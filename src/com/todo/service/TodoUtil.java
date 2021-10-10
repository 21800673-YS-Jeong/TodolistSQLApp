package com.todo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Statement;
import java.util.*;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, cate, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("\n[항목 추가]\n" + "제목 > ");
		title = sc.next().trim();
		
		if (list.isDuplicate(title)) {
			System.out.println("이미 사용 중인 제목입니다.");
			return;
		}
		
		System.out.print("카테고리 > ");
		cate = sc.next().trim();
		
		sc.nextLine();
		System.out.print("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.print("마감 날짜 > ");
		due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(title, desc, cate, due_date);
		if(list.addItem(t) > 0)
			System.out.println("항목이 추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		listAll(l);
		
		System.out.print("\n[항목 삭제]\n" + "삭제할 항목의 일련번호를 입력하세요 > ");
		int num = sc.nextInt();
		
		if(l.deleteItem(num) > 0)
			System.out.println("해당 항목이 삭제되었습니다.");
		
//		if (num <= l.getList().size() && num > 0 ) {
//			String check;
//			System.out.println(num + ". " + l.getList().get(num - 1).toString());
//			System.out.print("위 항목을 삭제하시겠습니까? (y/n) > ");
//			
//			do {
//				check = sc.next();
//				
//				switch(check) {
//					case "y":
//						l.deleteItem(num - 1);
//						System.out.println("삭제되었습니다.");
//						break;
//					
//					case "n":
//						break;
//				
//					default:
//						System.out.print("삭제하시려면 \"y\", 삭제하지 않으시려면 \"n\"을 입력하세요. > ");
//						break;
//				}
//			}while(!(check.equals("y") || check.equals("n")));
//		}
//		
//		else {
//			System.out.println("해당 일련번호의 항목을 찾을 수 없습니다.");
//		}
	}


	public static void updateItem(TodoList l) {
		
		String new_title, new_description, new_category, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		listAll(l);
		
		System.out.print("\n[항복 변경]\n" + "변경할 항목의 일련번호를 입력하세요 > ");
		int num = sc.nextInt();
		
//		if (num <= l.getList().size() && num > 0 ) {
//			System.out.println(num + ". " + l.getList().get(num - 1).toString());
//			l.deleteItem(num - 1);
//		}
//		else {
//			System.out.println("해당 일련번호의 항목을 찾을 수 없습니다.");
//			return;
//		}
		
		System.out.print("새로운 카테고리 > ");
		new_category = sc.next().trim();
		
		System.out.print("새로운 제목 > ");
		new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("이미 사용 중인 제목입니다.");
			return;
		}
		
		sc.nextLine();
		System.out.print("새로운 내용 > ");
		new_description = sc.nextLine().trim();
		
		System.out.print("새로운 마감 날짜 > ");
		new_due_date = sc.next().trim();
		
		TodoItem t = new TodoItem(new_title, new_description, new_category, new_due_date);
		t.setId(num);
		
		if(l.updateItem(t) > 0)
			System.out.println("항목이 변경되었습니다.");
	}
	
	public static void completeItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		listAll(l);
		
		System.out.print("\n[항목 완료]\n" + "완료할 항목의 일련번호를 입력하세요 > ");
		int num = sc.nextInt();
		
		if(l.completeItem(num) > 0)
			System.out.println("해당 항목이 완료 체크되었습니다.");
	}
	
	public static void findList(TodoList l, String keyWord) {
		int count = 0;
		
		for(TodoItem item : l.getList(keyWord)) {
			System.out.println(item.toString());
			count++;
		}
		
		if(count == 0) System.out.println("해당 키워드를 포함하는 항목을 찾을 수 없습니다.");
		else System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void findCateList(TodoList l, String keyword) {
		int count = 0;
		
		for(TodoItem item : l.getListCategory(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		
		if(count == 0) System.out.println("해당 키워드를 포함하는 항목을 찾을 수 없습니다.");
		else System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderBy, int ordering) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getOrderedList(orderBy, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, int num) {
		int count = 0;
		
		for (TodoItem item : l.getList(num)) {
			System.out.println(item.toString());
			count++;
		}
		
		if(count == 0) System.out.println("완료된 항목을 찾을 수 없습니다.");
		else System.out.println("총 " + count + "개의 항목을 찾았습니다.");
	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		
		for (String item : l.getCategories()) {
		    System.out.print(item + " ");
		    count++;
		}
		
		System.out.println(" 총 " + count + "개의 카테고리가 등록되어 있습니다.");
	}

	public static void loadList(TodoList l, String file) throws IOException {
		String s;
		int i = 0;
		
		File f = new File(file);
		if(!f.exists()) {
			f.createNewFile();
			System.out.println(file + " 파일이 없습니다.");
			return;
		}
		
		BufferedReader bf = new BufferedReader(new FileReader(f));
		
		while ((s = bf.readLine()) != null) {
			StringTokenizer token = new StringTokenizer(s, "##");
			TodoItem t = new TodoItem(token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken());
			t.setCurrent_date(token.nextToken());
			l.addItem(t);
			i++;
		}
		
		System.out.println(i + "개의 항목을 읽었습니다.");
	}

	public static void saveList(TodoList l, String file) throws IOException {
		FileWriter fw = new FileWriter(file);
		
		for(TodoItem item : l.getList()) {
			fw.write(item.toSaveString());
		}
		System.out.println("모든 데이터가 저장되었습니다.");
		fw.close();
	}
}
