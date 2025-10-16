package calculator;

import camp.nextstep.edu.missionutils.Console;


public class Application {
    public static void main(String[] args) {
        var input = getInput();
        var split = getSplitWord(input);

        calculate(input, split);
    }

    //추출값 덧셈
    private static void calculate(String input, String split) {
        String[] numbers = input.split(split);
        int sum = 0;

        for (String number : numbers) {
            sum += Integer.parseInt(number);
        }

        System.out.println("결과 : " + sum);
    }

    //입력값 & 검증
    private static String getInput() {
        System.out.println("문자열을 입력해 주세요.");
        var input = Console.readLine();
            //음수 체크
            if(input.contains("-")) {
                throw new IllegalArgumentException();
            }
            //공백 = 0
            if(input.isEmpty()) {
                return "0";
            }
        return input;
    }

    //커스텀 구분자 확인
    private static String getSplitWord(String input) {
        var basic = "[,:]";
         //커스텀 구분자
        if(input.startsWith("//")) {
            String custom = getInput().substring(2,3);
            basic = basic + "|" + custom;

        }
        return basic;
    }

}

