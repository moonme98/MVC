package spring.service.dice;

import java.util.Random;

/*
 * FileName : DiceA.java
 * ㅇ 주사위 Object Modeling
 * ㅇ 1 ~ 6 수를 랜덤하게  리턴
 */
public interface Dice {
	//Method (getter/setter)
	public int getValue();
	
	//==> 주사위를 던저 무작위로 숫자 생산
	public void selectedNumber();
	
}//end of class