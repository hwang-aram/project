import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PuzzleFrame extends JFrame {

	public PuzzleButton btns[][];

	

	private int[] move = new int[4];
	long start = System.currentTimeMillis();
	
	
	

	PuzzleFrame() {
		
		setTitle("Puzzle Game" );
		setBounds(520,320,300,300);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		// setLayout(new FlowLayout());
		setLayout(new GridLayout(4, 4));

		init();

		setVisible(true);

	}

	public void init() {
		
		long start = System.currentTimeMillis();
		initComponent();
		initEvent();
		
		 
		

	}

	

	public void initComponent() {
		btns = new PuzzleButton[4][4];
		
		numSet();
	}

	public void numSet() {//버튼추가
		for (int row = 0; row < 4; row++) {
			for(int col = 0; col < 4; col++) {
				btns[row][col] = new PuzzleButton("");
				btns[row][col].p.x = row;
				btns[row][col].p.y = col;
				add(btns[row][col]);
			}
			
		}
		
		start();
	}
	
	private void start() {
		/*HashSet<Integer> number = new HashSet();
		int random;
		while(number.size() != 15) {
			random =(int)(Math.random()*15)+1;
			number.add(random);*/
		 //랜덤버튼
		LinkedHashSet<Integer> number = new LinkedHashSet<>();
		int random;
		while(number.size() != 15) {
			random =(int)(Math.random()*15)+1;
			number.add(random);
				
		}
		int r = 0;
		int c = 0;
		for(int s : number) {
			btns[r][c].setText(s+"");
			c++;
			if(c==4) {
				r++;
				c=0;
			}
		}
	
		btns[3][3].setEnabled(false);
	}
	
	public void initEvent() {
		
		MouseAdapter ma = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				compare((PuzzleButton)e.getComponent());
				complete();
			}

		};

		for (int row = 0; row < 4; row++) {
			for(int col = 0; col < 4; col++) {
				btns[row][col].addMouseListener(ma);
			}
			
		}
		
	}
	
	private void compare(PuzzleButton btn) {
		for(int row = 0; row < 4; row++) {
			for(int col = 0; col < 4; col++) {
				PuzzleButton targetBtn = btns[row][col];
				if(!targetBtn.isEnabled()) {
					//왼쪽
					if(targetBtn.p.x == btn.p.x-1 && targetBtn.p.y==btn.p.y) {
						pare(targetBtn, btn);
					}
					//오른쪽
					if(targetBtn.p.x == btn.p.x+1 && targetBtn.p.y==btn.p.y) {
						pare(targetBtn, btn);
					}
					//아래
					if(targetBtn.p.x == btn.p.x && targetBtn.p.y==btn.p.y+1) {
						pare(targetBtn, btn);
					}
					//위
					if(targetBtn.p.x == btn.p.x && targetBtn.p.y==btn.p.y-1) {
						pare(targetBtn, btn);
					}
					
				}
			}
		}
		
	}

	private void pare(PuzzleButton targetBtn, PuzzleButton btn) {
		targetBtn.setText(btn.getText());
		targetBtn.setEnabled(true);
		btn.setText("");
		btn.setEnabled(false);
		return;
	}
	
	
	private void complete(){
		int point =0;
		for(int row = 0; row < 4; row++) {
			for( int col = 0; col < 4; col++) {
				if(btns[row][col].getText().
						equals(String.valueOf(col+1+row*4))) {
					point++;
				}
				
			}
		}
		if(point ==15) {
			ending();
		}
	
	}
	

	private void ending() {
		long end = System.currentTimeMillis();
		String[] choices = { "다시시작", "종료" };
		int choice = JOptionPane.showOptionDialog(this, "축하합니다." +"소요 시간 : " + -1*(start-end)/1000 + "초" , "완성", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
		if (choice == 0) {
			start();
		} else {
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new PuzzleFrame();
		
	}
}
