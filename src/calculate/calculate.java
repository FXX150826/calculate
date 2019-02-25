package calculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.Integer.*;
class calculate
{
	public static void main(String[] args) 
	{
		new calculator("��С��ļ�����");
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
		win.setBounds(60,60,410,640);//���ô��ڴ�С����ʼλ��
		win.setResizable(false);//���ڴ�С���ɸ���
		win.setLayout(null);

		button = new JPanel();//ʵ�������������ڷ��ü���������
		button.setBounds(0,200,400,400);
		button.setLayout(null);
//��������������
		date = new JButton[20];
		date[1] = new JButton("C");
		date[6] = new JButton("��");
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
		date[5] = new JButton("��");
		date[10] = new JButton("0");
		date[15] = new JButton(".");
		date[19] = new JButton("=");
		
		font = new Font("���Ŀ���",Font.BOLD,32);//��������
		int x,y;
		for(int i=1;i<20;i++)
		{
			x=((i-1)/5)*100;
			y=((i-1)%5)*80;
			date[i].setBounds(x,y,100,80);//���ð���λ��
			date[i].setFont(font);
			date[i].addActionListener(this);//��Ӽ����
		}
		date[19].setBounds(300,240,100,160);
		
		
		showText = new JTextArea();
		showText.setLineWrap(true);//�ı������ı������Զ�����
		showText.setBounds(0,0,410,200);

		textColor = new Color(109,120,250);
		showText.setBackground(textColor);//�����ı��򱳾�ɫ
		showText.setFont(font);//�����ı�������

//���������ı�����������������
		for(int i=1;i<20;i++)
		{
			button.add(date[i]);
		}
		win.add(button);
		win.add(showText);

		win.setVisible(true);
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
//�¼�����
	public void actionPerformed(ActionEvent e)
	{
		JButton date = (JButton)e.getSource();
		String name = new String(date.getText());
		if(name.equals("C"))
		{
			name = new String();
			showText.setText(name);//����ı�
			front = 0;//����ı������ı�
			ftextlen = 0;//�ı������ı�����Ϊ0
		}
		else if(name.equals("��"))//�˸�
		{//��ȡ�ı����������ı���ɾȥ���һ���ַ��������ı������ı�
			String caltext = new String(showText.getText());
			int i = caltext.length()-1;
			showText.setText(caltext.substring(0,i));
			caltext = null;
		}
		else if(name.equals("="))//����
		{
			showText.append(name);
			String caltext = new String(showText.getText());
			caltext = caltext.substring(ftextlen);//��ȡ�ı�����δ���м��������
		//����ʽ�ж�ȡһ����
			char a,b ='+',c;
			int i=0,n=0;
			double sum1=0,sum2=0;
			do{
				n=0;
				c = '0';
				sum1=0;
				do{//�������Ϊ��־�����ζ�ȡ����
					a = caltext.charAt(i);//���λ�ȡ�ı��е��ַ�
					if((a<='9'&&a>='0')||a == '.')
					{
						if(a == '.')
							c = '.';
						if(a != '.')
							sum1 = sum1*10+a-'0';
						if(c == '.')//�����г���С���㣬��¼С��λ��
							n++;
					}
					i++;
				}while(((a<='9'&&a>='0')||a == '.')&&i<caltext.length());
				if(c == '.')
					n--;
				sum1 = sum1/(Math.pow(10,n));//����С�����ƶ�С����
		//�ж�����
				if(a == '+'||a == '-'||a == '=')//�����Ҳ���������ȼ�һ��������'+'
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
				else if(a == '*'|| a == '/')//�����Ҳ������һ�����������
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
				else if(a == '��')//�����Ҳ������һ���������
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
				b = a;//��¼�������Ҳ������Ϊ��һ������������
			}while(a != '='&&i<caltext.length());

			showText.append(String.valueOf(sum));//����ʽ������������ı���
			front = 1;//�ı�����������
			caltext = new String(showText.getText());
			ftextlen = caltext.length()-(String.valueOf(sum)).length();//��¼�Ѽ�����ʽ����
			sum=0;
		}
		else//���ı�����ʾ��ʽ
		{
				showText.append(name);
		}
	}
}


