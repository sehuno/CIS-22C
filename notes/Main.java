// Using a LinkedQueue<String> parameter which contains the tokens of a PREFIX Expression (operator left right), write a recursive public static method to evaluate (and return as a double) the expression (or partial expression) using the following pseudocode: (the queue of strings is already filled)

public class Main {
  // prefixEvaluate(queue): double
  public static Double prefixEvaluate(QueueInterface<String> queue)
  {
    // IF the queue is not empty Then
    if(!queue.isEmpty()) 
    {
      // Get the next token in the queue (dequeue from our LinkedQueue)
      String token = queue.dequeue();
      if(isOperator(token))
      {
        // ASSIGN to left, prefixEvaluate(queue)
        Double left = prefixEvaluate(queue);
        // ASSIGN to right, prefixEvaluate(queue)
        Double right = prefixEvaluate(queue);
        switch(token.charAt(0))
        {
          case '+': 
            return left+right;
          case '-': 
            return left-right;
          case '*': 
            return left*right;
          case '/': 
            return left/right;
          default:
            return 0.0;
        }
      }
      else
      {
        // ASSIGN to operand, token converted to a double
        Double operand = Double.parseDouble(token);
        return operand;
      }
    }
    else
      return 0.0;
  }

  public static boolean isOperator(String token) 
  {
    if(token.length() == 1) 
    {
      if(token.equals(new String("*")))
        return true;
      if (token.equals(new String("/")))
        return true;
      if(token.equals(new String("+")))
        return true;
      if (token.equals(new String("-")))
        return true;
      return false;
    } 
    else 
      return false;   
  }

  public static void main(String args[])
  {
    LinkedQueue<String> string_queue = new LinkedQueue<>();
    string_queue.enqueue("-");
    string_queue.enqueue("932.0");
    string_queue.enqueue("78.0");
    System.out.println(prefixEvaluate(string_queue));
  }

}


