package cs228hw2;

import java.util.Scanner;

/**
 * @author Simeon Steward
 * A calculator using postfix notation.
 * Uses numbers "-" "+" "neg" "abs"
 * "end" will end the stream"
 */
public class calculator {
    public static void main(String[] args) {
        Deque228<String> inputQueue = new Deque228<>();
        Scanner scan = new Scanner(System.in);
        while (scan.hasNext()){
            String temp = scan.next();
            if(AmusingPreciseNumber.isNumber(temp)|| temp.equals("+") || temp.equals("-") || temp.equals("abs") || temp.equals("neg")){
                inputQueue.offer(temp);
            } else if(temp.equals("end")){
                break;
            }else{
                System.out.println("Invalid Input, try again");
            }
        }
        scan.close();
        Deque228 <AmusingPreciseNumber> numberStack = new Deque228<>();
        while (!inputQueue.isEmpty()){
            String temp = inputQueue.poll();
            try{
                if(temp.equals("+")) {
                    AmusingPreciseNumber num = numberStack.pop();
                    num.add(numberStack.pop());
                    numberStack.push(num);
                }else if(temp.equals("-")){
                    AmusingPreciseNumber num1 = numberStack.pop();
                    AmusingPreciseNumber num2 = numberStack.pop();
                    num2.subtract(num1);
                    numberStack.push(num2);
                }else if(AmusingPreciseNumber.isNumber(temp)){
                    numberStack.push(new AmusingPreciseNumber(temp));
                }else if(temp.equals("neg")){
                   numberStack.peek().negate();
            }else if(temp.equals("abs")){
                    numberStack.peek().abs();
                }
            }catch(Exception e){
                System.out.println("Error Message");
                return;
            }
        }
        System.out.println(numberStack.peek().toString());

    }
}
