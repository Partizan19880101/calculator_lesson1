class Calculator {
    //available symbols
    private static final String[] allowedCharacters = "123456789()+-*%/**"

    static def calculate(String input) {
        checkCalculatorExpression.call(input)
        String output = getRpnExpression.call(input)
        return countResult.call(output)
    }

    static def getRpnExpression = { String input ->
        def output = new StringBuilder()
        def operators = new Stack<>()

        for (int i = 0; i < input.length(); i++) {

            if (isDelimiter(input[i]))
                continue

            if (input.charAt(i).isDigit()) {

                while (!isDelimiter(input[i]) && !isOperator(input[i])) {
                    output.append(input[i])
                    i++

                    if (i == input.length()) break
                }

                output.append(" ")
                i--
            }

            if (isOperator(input[i])) {
                if (input[i].toString() == '(')
                    operators.push(input[i])
                else if (input[i].toString() == ')') {

                    def s = operators.pop()

                    while (s.toString() != '(') {
                        output.append(s.toString()).append(' ')
                        s = operators.pop()
                    }
                } else {
                    if (operators.size() > 0)
                        if (getPriority(input[i]) <= getPriority(operators.peek()))
                            output.append(operators.pop().toString() + " ")

                    operators.push(input[i])
                }
            }
        }

        while (operators.size() > 0) {
            output.append(operators.pop()).append(" ")
        }
        return output
    }

    static def countResult = { String input ->
        def result
        def resultStack = new Stack<>()

        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i).isDigit()) {
                StringBuilder tempResult = new StringBuilder()

                while (!isDelimiter(input[i]) && !isOperator(input[i])) {
                    tempResult.append(input[i])
                    i++
                    if (i == input.length()) break
                }
                resultStack.push(Double.parseDouble(tempResult.toString()))
                i--
            } else if (isOperator(input[i])) {

                def a = resultStack.pop()
                def b = resultStack.pop()

                switch (input[i]) {
                    case '+': result = b + a; break
                    case '-': result = b - a; break
                    case '*': result = b * a; break
                    case '/': result = b / a; break
                    case '%': result = b % a; break
                    case '**': result = b ** a; break
                }
                resultStack.push(result)
            }
        }
        return resultStack.peek()
    }

    static private def isDelimiter(String c) {
        return " =".indexOf(c) != -1
    }

    static private def isOperator(def a) {
        return "+-/*%^()**".indexOf(a) != -1
    }

    static private def getPriority(def s) {
        switch (s) {
            case '(': return 0
            case ')': return 1
            case '+': return 2
            case '-': return 3
            case '*': return 4
            case '/': return 5
            case '%': return 6
            case '**': return 7
            default: return 8
        }
    }

    static def checkCalculatorExpression = { String input ->
        for (int i = 0; i<input.length(); i++) {
            if (!(input[i] in allowedCharacters)) {
                String invalidCharacter = input[i]
                throw new IllegalArgumentException("Invalid calculator sybmol: \"$invalidCharacter\". Allowed symbols are: $allowedCharacters")
            }
            if(isOperator(input[i]) && input[i] == input[i-1]) {
                throw new IllegalArgumentException("Invalid mathematical expression: $input.")
            }
        }
    }
}