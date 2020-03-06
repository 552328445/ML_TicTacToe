package com;

import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class GameRunner
{
    //note: for X, a tie is treated like a loss. For O, a tie is treated like a win
        static private ImageIcon xWin = new ImageIcon("XWins.png");
        static private ImageIcon oWin = new ImageIcon("OWins.png");
        static private ImageIcon tie = new ImageIcon("Tie.png");
        static private ImageIcon title = new ImageIcon("Title.png");
        static private JFrame f = new JFrame();
        static private JButton b = new JButton();
        static private Brutus xAI;
        static private Brutus oAI;
    @SuppressWarnings("deprecation")
	public static void main(String args[])
    {

        Game board = new Game();
        b.setIcon(title);
        f.setBounds(0, 0, 500, 500);
        f.add(b);
        b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    //f.hide();
                    //WORK HERE
                    synchronized(f) {
                         f.notify();
                    }
                }
            });
        f.show();
        
        System.out.println("Choose (enter a number 1-4): ");
        System.out.println("1: Play as x against AI");
        System.out.println("2: Play as o against AI");
        System.out.println("3: Watch the AIs play each other");
        System.out.println("4: Play with a friend");
        boolean OsAI;
        boolean XsAI;
        switch (getInt(4, 1)){ //doesn't work
            case 1:
            OsAI = true;
            XsAI = false;
            break;
            case 2:
            OsAI = false;
            XsAI = true;
            break;
            case 3:
            OsAI = true;
            XsAI = true;
            break;
            case 4:
            OsAI = false;
            XsAI = false;
            break;
            default:
            System.out.println("Something went terribly wrong");
            OsAI = false;
            XsAI = false;
            break;
        }
        xAI = new Brutus(XsAI);//Villain always plays o
        oAI = new Brutus(OsAI);//Hero always plays x
        
        int Xwins = 0;
        int Owins = 0;
        
        System.out.println("");
        System.out.print("Enter the number of games you want to play(0 to 10,000): ");
        int runs = getInt(10000, 0);
        System.out.println("");
        
        for (int r = runs; r > 0; r--)
        {
            String bd = board.theBoard();
            int turn = 0;
            while (true)//infinite loop, uses break to escape
            {
                //check if anybody won or tied
                if(bd.substring(0, 3).equals("xxx") ||
                   bd.substring(3, 6).equals("xxx") ||
                   bd.substring(6, 9).equals("xxx") ||
                   (bd.substring(0, 1) + bd.substring(3, 4) + bd.substring(6, 7)).equals("xxx") ||
                   (bd.substring(1, 2) + bd.substring(4, 5) + bd.substring(7, 8)).equals("xxx") ||
                   (bd.substring(2, 3) + bd.substring(5, 6) + bd.substring(8, 9)).equals("xxx") ||
                   (bd.substring(0, 1) + bd.substring(4, 5) + bd.substring(8, 9)).equals("xxx") ||
                   (bd.substring(2, 3) + bd.substring(4, 5) + bd.substring(6, 7)).equals("xxx"))
                {
                    //System.out.println("Crosses win!");
                    b.setIcon(xWin);
                    //WORK HERE
                    f.show();
                    try {
                        synchronized(f)
                        {
                            f.wait();
                        }
                    } catch (java.lang.InterruptedException e)
                    {}
                    if (turn == 5 || turn == 6)
                    {
                        oAI.learn(1);
                        xAI.learn(7);
                    } else if (turn == 7 || turn == 8)
                    {
                        oAI.learn(2);
                        xAI.learn(6);
                    } else if (turn == 9)
                    {
                        oAI.learn(3);
                        xAI.learn(5);
                    }
                    
                    Xwins++;
                    //board.gameOver(xWin);
                    break;
                }
                else if (bd.substring(0, 3).equals("ooo") ||
                   bd.substring(3, 6).equals("ooo") ||
                   bd.substring(6, 9).equals("ooo") ||
                  (bd.substring(0, 1) + bd.substring(3, 4) + bd.substring(6, 7)).equals("ooo") ||
                  (bd.substring(1, 2) + bd.substring(4, 5) + bd.substring(7, 8)).equals("ooo") ||
                  (bd.substring(2, 3) + bd.substring(5, 6) + bd.substring(8, 9)).equals("ooo") ||
                  (bd.substring(0, 1) + bd.substring(4, 5) + bd.substring(8, 9)).equals("ooo") ||
                  (bd.substring(2, 3) + bd.substring(4, 5) + bd.substring(6, 7)).equals("ooo"))
                {
                    //WORK HERE
                    //System.out.println("Nots win!");
                    b.setIcon(oWin);
                    f.show();
                    try {
                        synchronized(f)
                        {
                            f.wait();
                        }
                    } catch (java.lang.InterruptedException e)
                    {}
                    if (turn == 5 || turn == 6)
                    {
                        oAI.learn(7);
                        xAI.learn(1);
                    } else if (turn == 7 || turn == 8)
                    {
                        oAI.learn(6);
                        xAI.learn(2);
                    } else if (turn == 9) // I'm pretty sure this is unreachable, o can't win in 9 turns
                    {
                        oAI.learn(5);
                        xAI.learn(3);
                    }
                    Owins++;
                    //board.gameOver(oWin);
                    break;
                }
                else if (!(bd.substring(0, 1).equals("1") || bd.substring(1, 2).equals("2") ||
                           bd.substring(2, 3).equals("3") || bd.substring(3, 4).equals("4") ||
                           bd.substring(4, 5).equals("5") || bd.substring(5, 6).equals("6") ||
                           bd.substring(6, 7).equals("7") || bd.substring(7, 8).equals("8") ||
                           bd.substring(8, 9).equals("9")))
                {
                    //WORK HERE
                    //System.out.println("Tie.");
                    b.setIcon(tie);
                    f.show();
                    try {
                        synchronized(f)
                        {
                            f.wait();
                        }
                    } catch (java.lang.InterruptedException e)
                    {}
                    oAI.learn(4);
                    xAI.learn(4);
                    //fboard.gameOver(tie);
                    break;
                }
                else//if nobody won yet
                {
                    if (turn % 2 == 0)//if it's my turn
                    {
                        if (XsAI)//If AI x is on, use AI
                        {
                            int x = xAI.move(bd, true);
                            bd = board.xPlays(bd, x);
                            turn++;
                        }
                        else//Otherwise use human input
                        {
                            System.out.println("Crosses Turn:");
                            board.printGame(bd);
                            int x = 0;
                             //get a number and be sure not to "write on top of" another
                             while (x == 0 || bd.substring(x - 1, x).equals("x") || bd.substring(x - 1, x).equals("o"))
                             {
                                // x = getInt(9, 1);
                                x = board.waitForButton();
                             }
                             System.err.println("You moved as x " + x);
                            xAI.observe(bd, x);//add this in later too
                            bd = board.xPlays(bd, x);//make the move
                            turn++;
                        }
                    }
                    else
                    {
                        if (OsAI)//If o AI is on, use it
                        {
                            int x = oAI.move(bd, false);
                            bd = board.oPlays(bd, x);
                            turn++;
                        }
                        else//Otherwise use human input
                        {
                            System.out.println("Nots Turn:");
                            board.printGame(bd);
                            int x = 0;
                            //get a number and be sure not to "write on top of" another
                            while (x == 0 || bd.substring(x - 1, x).equals("x") || bd.substring(x - 1, x).equals("o"))
                            {
                                // x = getInt(9, 1);
                                x = board.waitForButton();
                            }
                            System.err.println("You moved as o " + x);
                            oAI.observe(bd, x);
                            bd = board.oPlays(bd, x);
                            turn++;
                        }
                    }       
                }
            }
            board.printGame(bd);
            System.out.println(runs - r + 1);
        }
        if (OsAI) {
            oAI.show();
        }
        System.out.println("Wins: " + Owins);
        if (XsAI) {
            xAI.show();
        }
        System.out.println("Wins: " + Xwins);
    }
//    public static int getInt(int max, int min) //WORK HERE Scanner is broken
//    {
//        Scanner scan = new Scanner(System.in);
//        int n = 0;
//        try { //This makes sure the user gives a valid input.
//        //	while (!scan.hasNextInt()) {
//        //		try {
//		//			Thread.sleep(1000);
//		//		} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//		//			e.printStackTrace();
//		//		}
//        
//        //	} 
//        	n = scan.nextInt();
//            System.out.println(n);
//            if (n <= max && n >= min) {
//            scan.close();
//            return n;
//            } else {
//                System.out.println("Invalid input.");
//                System.out.println("");
//                scan.close();
//                return getInt(max, min);
//            }
//        } catch (java.util.InputMismatchException e) 
//        {
//            System.out.println("Invalid input.");
//            System.out.println("");
//            scan.close();
//            return getInt(max, min);
//        } catch (NoSuchElementException e)
//        {
//        	return getInt(max, min);
//        }
//        
//        
//    }
    public static int getInt(int max, int min) 
    {
        Scanner scan = new Scanner(System.in);
        int n = 0;
        try { //This makes sure the user gives a valid input.
            n = scan.nextInt();
            System.out.println(n);
            if (n <= max && n >= min) {
            //scan.close();
            return n;
            } else {
                System.out.println("Invalid input.");
                System.out.println("");
                //scan.close();
                return getInt(max, min);
            }
        } catch (java.util.InputMismatchException e) 
        {
            System.out.println("Invalid input.");
            System.out.println("");
            //scan.close();
            return getInt(max, min);
        }
        
    }
    
}

