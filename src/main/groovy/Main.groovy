class Main {

    static void main(String[] args) {

        Calculator calculator = new Calculator()

        assert calculator.calculate("1+1") == 2
        assert calculator.calculate("3+2*2") == 7
        assert calculator.calculate("(2+3)*2") == 10
        assert calculator.calculate("(5*12)-(17-3)") == 46

        int sum = calculator.calculate("9/3")

        println("Your have result ${sum}")
    }
}
