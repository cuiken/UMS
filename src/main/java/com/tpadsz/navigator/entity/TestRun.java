package com.tpadsz.navigator.entity;

import java.util.ArrayList;

public class TestRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Navigator nav = new Navigator();
		Top top = new Top();
		CenterLeft centerLeft = new CenterLeft();
		CenterRight centerRight = new CenterRight();
		Bottom bottom = new Bottom();
		Button b1 = new Button(1L, "", 1, false, "1", "3,1", "", "����", "");
		Button b2 = new Button(2L, "", 1, false, "1", "3,1", "", "����", "");
		Button b3 = new Button(3L, "", 1, false, "1", "3,1", "", "����", "");
		Button b4 = new Button(4L, "", 1, false, "1", "3,1", "", "����", "");
		Button b6 = new Button(5L, "", 1, false, "1", "3,1", "", "����", "");
		Button b7 = new Button(6L, "", 1, false, "1", "3,1", "", "����", "");
		Button b8 = new Button(7L, "", 1, false, "1", "3,1", "", "����", "");
		Button b9 = new Button(8L, "", 1, false, "1", "3,1", "", "����", "");
		Button b10 = new Button(9L, "", 1, false, "1", "3,1", "", "����", "");
		Button b11 = new Button(10L, "", 1, false, "1", "3,1", "", "����", "");
		Button b12 = new Button(11L, "", 1, false, "1", "3,1", "", "����", "");
		Button b5 = new Button(12L, "", 1, false, "1", "3,1", "", "����", "");

		ArrayList<Button> topButtons = new ArrayList<Button>(3);
		topButtons.add(b1);
		topButtons.add(b2);
		topButtons.add(b3);

		ArrayList<Button> leftButtons = new ArrayList<Button>(1);
		leftButtons.add(b4);

		ArrayList<Button> rightButtons = new ArrayList<Button>(1);
		leftButtons.add(b5);

		ArrayList<Button> bottomButtons = new ArrayList<Button>(1);
		bottomButtons.add(b6);
		bottomButtons.add(b7);
		bottomButtons.add(b8);
		bottomButtons.add(b9);
		bottomButtons.add(b10);
		bottomButtons.add(b11);
		
		

		top.setTemplate("1");
		top.setButtons(topButtons);
		centerLeft.setTemplate("default");
		centerLeft.setButtons(leftButtons);
		centerRight.setTemplate("default");
		centerRight.setButtons(rightButtons);
		bottom.setTemplate("default");
		bottom.setButtons(bottomButtons);

		nav.setTop(top);
		nav.setBottom(bottom);
		nav.setLeft(centerLeft);
		nav.setRight(centerRight);
		
		System.out.println(Navigator.marshall(nav));

	}

}
