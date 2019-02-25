package calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Integer.*;
class calculate
{
	public static void main(String[] args) 
	{
		new calculator("飞小象的计算器");
	}
}
class calculator extends JFrame implements ActionListener
{
	JFrame win;
	JButton date[];
	JPanel button;
	JTextArea showText;
	Color textColor;
	Font font;
	double sum = 0,fsum = 0;
	int front = 0,ftextlen = 0;
	//PoliceStation police;
	calculator(String s)
	{
		win = new JFrame(s);
		//win.setBounds(60,60,420,650);
		win.setBounds(60,60,410,640);//设置窗口大小和起始位置
		win.setResizable(false);//窗口大小不可更改
		win.setLayout(null);

		button = new JPanel();//实例化容器，用于放置计算器按键
		button.setBounds(0,200,400,400);
		button.setLayout(null);
//创建计算器按键
		date = new JButton[20];
		date[1] = new JButton("C");
		date[6] = new JButton("←");
		date[11] = new JButton("/");
		date[16] = new JButton("*");
		date[2] = new JButton("7");
		date[7] = new JButton("8");
		date[12] = new JButton("9");
		date[17] = new JButton("-");
		date[3] = new JButton("4");
		date[8] = new JButton("5");
		date[13] = new JButton("6");
		date[18] = new JButton("+");
		date[4] = new JButton("1");
		date[9] = new JButton("2");
		date[14] = new JButton("3");
		date[5] = new JButton("％");
		date[10] = new JButton("0");
		date[15] = new JButton(".");
		date[19] = new JButton("=");
		
		font = new Font("华文楷体",Font.BOLD,32);//字体设置
		int x,y;
		for(int i=1;i<20;i++)
		{
			x=((i-1)/5)*100;
			y=((i-1)%5)*80;
			date[i].setBounds(x,y,100,80);//设置按键位置
			date[i].setFont(font);
			date[i].addActionListener(this);//添加监控器
		}
		date[19].setBounds(300,240,100,160);
		
		
		showText = new JTextArea();
		showText.setLineWrap(true);//文本框中文本内容自动换行
		showText.setBounds(0,0,410,200);

		textColor = new Color(109,120,250);
		showText.setBackground(textColor);//设置文本框背景色
		showText.setFont(font);//设置文本框字体

//将按键及文本框放入计算器窗体中
		for(int i=1;i<20;i++)
		{
			button.add(date[i]);
		}
		win.add(button);
		win.add(showText);

		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
//事件处理
	public void actionPerformed(ActionEvent e)
	{
		JButton date = (JButton)e.getSource();
		String name = new String(date.getText());
		if(name.equals("C"))
		{
			name = new String();
			showText.setText(name);//清空文本
			front = 0;//标记文本框无文本
			ftextlen = 0;//文本框中文本长度为0
		}
		else if(name.equals("←"))//退格
		{//获取文本框中所有文本，删去最后一个字符，覆盖文本框中文本
			String caltext = new String(showText.getText());
			int i = caltext.length()-1;
			showText.setText(caltext.substring(0,i));
			caltext = null;
		}
		else if(name.equals("="))//计算
		{
			showText.append(name);
			String caltext = new String(showText.getText());
			caltext = caltext.substring(ftextlen);//获取文本框中未进行计算的内容
		//从算式中读取一个数
			char a,b ='+',c;
			int i=0,n=0;
			double sum1=0,sum2=0;
			do{
				n=0;
				c = '0';
				sum1=0;
				do{//以运算符为标志，依次读取数据
					a = caltext.charAt(i);//依次获取文本中的字符
					if((a<='9'&&a>='0')||a == '.')
					{
						if(a == '.')
							c = '.';
						if(a != '.')
							sum1 = sum1*10+a-'0';
						if(c == '.')//数字中出现小数点，记录小数位数
							n++;
					}
					i++;
				}while(((a<='9'&&a>='0')||a == '.')&&i<caltext.length());
				if(c == '.')
					n--;
				sum1 = sum1/(Math.pow(10,n));//整理小数，移动小数点
		//判断运算
				if(a == '+'||a == '-'||a == '=')//数据右侧运算符优先级一定不高于'+'
				{
					if(b == '+')
						sum2 = sum2 + sum1;
					else if(b == '-')
						sum = sum - sum1;
					else if(b == '*')
						sum2 = sum2 * sum1;
					else if(b == '/')
						sum2 = sum2 / sum1;
					sum = sum + sum2;
					sum2 = 0;
				}
				else if(a == '*'|| a == '/')//数据右侧运算符一定不低于左侧
				{
					if(b == '+')
						sum2 = sum1;
					else if(b == '-')
						sum2 = 0-sum1;
					else if(b == '*')
						sum2 = sum2*sum1;
					else if(b == '/')
						sum2 = sum2/sum1;
				}
				else if(a == '％')//数据右侧运算符一定高于左侧
				{
					if(b == '+')
						sum2 =sum1/100;
					else if(b == '-')
						sum2 = 0-sum1/100;
					else if(b == '*')
						sum2 = sum2*sum1/100;
					else if(b == '/')
						sum2 = sum2/(sum1/100);
				}
				b = a;//记录该数据右侧运算符为下一数据左侧运算符
			}while(a != '='&&i<caltext.length());

			showText.append(String.valueOf(sum));//将算式计算结果输出到文本框
			front = 1;//文本框中有内容
			caltext = new String(showText.getText());
			ftextlen = caltext.length()-(String.valueOf(sum)).length();//记录已计算算式长度
			sum=0;
		}
		else//在文本框显示算式
		{
				showText.append(name);
		}
	}
}


