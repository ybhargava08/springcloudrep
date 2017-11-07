package com.yb.retreiveandsave.tstoperations;

public class TSTNode {
	
	TSTNode left;
	TSTNode middle;
	TSTNode right;
	char val;
	boolean isWordEnd,isLeaf;
	int arrayPos=0;

	public TSTNode(char value){
		this.val = value;
	}
}
