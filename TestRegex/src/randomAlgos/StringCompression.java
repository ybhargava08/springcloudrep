package randomAlgos;

import java.util.Stack;

public class StringCompression {

	public static void main(String[] args) {

		String input = "aaaabbacddda";
		
		System.out.println(compress(input));
		
	}

	private static Stack<String> compress(String input) {
		Stack<String> stack = new Stack<String>();
		int counter=1;
		for(int i=0;i<input.length();i++) {
			String temp = input.substring(i, i+1);
			
			if(stack.isEmpty()) {
				stack.push(temp);
			}else {
				if(!stack.peek().equalsIgnoreCase(temp)) {
					stack.push(String.valueOf(counter));
					stack.push(temp);
                    counter=1;
				}else {
					counter++;
				}
			}
			
			if(i==input.length()-1) {
				stack.push(String.valueOf(counter));
			}
		}
		
		return stack;
	}
	
}
