package com.example.calcurator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    /**
     * 文字列型の計算を行い文字列型の答えを返却する
     * example1 "1 + 2 × 3 + 4" -> return "11.0"
     * example2 "1 + 2 × ((-3) + (-4) × 5 + 6)" -> return "-33.0"
     * example3 "((1 + 2) × (3 + 4) + ((3 + 5) × 6) × (5 × (2 × 2)))" -> return "981.0"
     *
     * @param calcText 計算式の文字列
     * @return 渡された計算式の計算結果の文字列
     */
    public String calc(String calcText) {
        String newCalcText = calcText;
        boolean existLeftPare = existLeftPare(newCalcText);
        Parenthesis parenthesis = null;
        while (existLeftPare) {
            parenthesis = lastOncePare(newCalcText);
            String partCalcText = newCalcText.substring(parenthesis.getStart() + 1, parenthesis.getEnd());

            String result = calcText(partCalcText);
            System.out.println("Before newCalcText: " + newCalcText);

            String frontText = newCalcText.substring(0, parenthesis.getStart());
            String backText = newCalcText.substring(parenthesis.getEnd() + 1);
            newCalcText = frontText + result + backText;
            System.out.println("After newCalcText: " + newCalcText);

            existLeftPare = existLeftPare(newCalcText);
        }
        String ans = calcText(newCalcText);
        System.out.println("ans: " + ans);
        return ans;
    }

    /**
     * 与えられた文字列から数値型に変換し、計算結果の値をString値で返却する
     * example calcText("1 + 2 × 3 + 4") -> return "11.0"
     * example calcText("1 + 2 × 3 + 2 × 3") -> return "13.0"
     *
     * @param calcText 文字列で表した数式 example -> "1 + 2 + 4 ÷ 5" "2 × 5 ÷ 2.0 + 3.3 % 5"
     * @return
     */
    public String calcText(String calcText) {
        // 文字列数値が単一の場合はそのまま返却
        Pattern singleNumPattern = Pattern.compile("\\-\\d+");
        Matcher singleMatcher = singleNumPattern.matcher(calcText);
        if (singleMatcher.matches()) {
            return calcText;
        }

        String newCalcText = new StringBuilder(calcText).append(" ").toString();
        Pattern numPattern = Pattern.compile("\\-\\d+\\.\\d+|\\d+\\.\\d+|\\d+\\s|\\-\\d+");
        Matcher numMatcher = numPattern.matcher(newCalcText);

        Pattern termPattern = Pattern.compile("[\\s][\\+\\-\\%×÷][\\s]");
        Matcher termMatcher = termPattern.matcher(newCalcText);
        // 数値の配列
        ArrayList<String> numberArray = new ArrayList<>();
        // 演算子の配列
        ArrayList<String> termArray = new ArrayList<>();

        while (numMatcher.find()) {
            numberArray.add(newCalcText.substring(numMatcher.start(), numMatcher.end()).trim());
        }

        while (termMatcher.find()) {
            termArray.add(newCalcText.substring(termMatcher.start(), termMatcher.end()).trim());
        }

        // 計算の優先順位度の配列
        ArrayList<Integer> priorityArray = new ArrayList<>();
        // 計算の順番の配列
        List<Integer> calcOrder = calcOrder(termArray);

        String ans = partCalc(numberArray, termArray, calcOrder);
        return ans;
    }

    /**
     * 文字列型のリスト情報・算術演算子のリスト・優先順位のリスト
     * から計算結果を算出し返却するメソッド
     *
     * @param numArray   文字列型の数値の入ったリスト
     * @param termArray  文字列型の算術演算子の入ったリスト
     * @param orderArray Integer型の優先順位度の入ったリスト
     * @return
     */
    public String partCalc(List<String> numArray,
                           List<String> termArray, List<Integer> orderArray) {
//        List<Double> numIntArray = numArray.stream().map(val -> Double.parseDouble(val)).collect(Collectors.toList());
//        List<Double> numIntArray = new ArrayList<>();
//        for (int i = 0; i < numArray.size(); i++) {
//            Double value = Double.parseDouble(numArray.get(i));
//            numIntArray.add(value);
//        }

        // 計算結果
        int ans = 0;
        // 処理カウンター
        int counter = 0;
        // 計算処理
        while (numArray.size() != 1) {
            int calcIndex = 0; // 計算するIndex
            // Indexの位置を取得
            for (int i = 0; i < orderArray.size(); i++) {
                if (orderArray.get(i) == counter) {
                    calcIndex = i;
                    counter++;
                    break;
                }
            }

            BigDecimal leftNum = new BigDecimal(numArray.get(calcIndex));
            BigDecimal rightNum = new BigDecimal(numArray.get(calcIndex + 1));

            // 計算結果
//            Double calcAns = 0.0;
            String calcAns = "";
            switch (termArray.get(calcIndex)) {
                case "+":
//                    calcAns = numIntArray.get(calcIndex) + numIntArray.get(calcIndex + 1);
                    calcAns = leftNum.add(rightNum).toPlainString();
                    break;
                case "-":
//                    calcAns = numIntArray.get(calcIndex) - numIntArray.get(calcIndex + 1);
                    calcAns = leftNum.subtract(rightNum).toPlainString();
                    break;
                case "×":
//                    calcAns = numIntArray.get(calcIndex) * numIntArray.get(calcIndex + 1);
                    calcAns = leftNum.multiply(rightNum).toPlainString();
                    break;
                case "÷":
//                    calcAns = numIntArray.get(calcIndex) / numIntArray.get(calcIndex + 1);
                    // java.lang.ArithmeticException: Division by zero

                    try{
                        calcAns = leftNum.divide(rightNum).toPlainString();
                    }catch (ArithmeticException ex){
                        // 少数代5位までで四捨五入
                        calcAns = leftNum.divide(rightNum, 5, RoundingMode.HALF_UP).toPlainString();
                    }
//                    // 少数代5位までで四捨五入
//                    calcAns = leftNum.divide(rightNum, 5, RoundingMode.HALF_UP).toPlainString();
                    break;
                case "%":
//                    calcAns = numIntArray.get(calcIndex) % numIntArray.get(calcIndex + 1);
                    calcAns = leftNum.remainder(rightNum).toPlainString();
                    break;
            }
            // 計算結果から各配列の変換
            termArray.remove(calcIndex);
            orderArray.remove(calcIndex);
//            numIntArray.remove(calcIndex + 1);
//            numIntArray.remove(calcIndex);
//            numIntArray.add(calcIndex, calcAns);
            numArray.remove(calcIndex + 1);
            numArray.remove(calcIndex);
            numArray.add(calcIndex, calcAns);
        }
//        System.out.println("計算結果: " + numIntArray);
//        return numIntArray.get(0);
        return numArray.get(0);
    }

    /**
     * 演算子のリストから優先順位度を計算し計算順序の優先度の配列を返却する
     * 優先度の値は0からとなる
     * example [×, ÷, +, ×] -> [0,1,3,2] , [+, ÷, -, ×] -> [2, 0, 3, 1]
     *
     * @param termArray
     * @return
     */
    private List<Integer> calcOrder(List<String> termArray) {
        // 計算の優先順位度の配列
//        List<Integer> priorityArray = termArray.stream().map(val -> {
//            int priority = Objects.equals(val, "+") || Objects.equals(val, "-") ? 1 : 2;
//            return priority;
//        }).collect(Collectors.toList());
        List<Integer> priorityArray = new ArrayList<>();
        for (int i = 0; i < termArray.size(); i++) {
            String priorityStr = termArray.get(i);
            int priority = priorityStr.equals("+") || priorityStr.equals("-") ? 1 : 2;
            priorityArray.add(priority);
        }

        // 計算の順番の配列
//        List<Integer> calcOrder =
//                Stream.iterate(0, i -> i + 1).limit(priorityArray.size()).collect(Collectors.toList());
        List<Integer> calcOrder = new ArrayList<>();
        for (int i = 0; i < priorityArray.size(); i++) {
            calcOrder.add(i);
        }
        int priorityHigh = 2; // 優先順位の高い数値
        int priorityLow = 1; // 優先順位が低い数値

        // 処理カウンター
        int counter = 0;
        // 優先順位の高い処理の初期化
        for (int i = 0; i < priorityArray.size(); i++) {
            if (priorityArray.get(i) == priorityHigh) {
                calcOrder.set(i, counter);
                counter++;
            }
        }

        // 優先順位の低い処理の初期化
        for (int i = 0; i < priorityArray.size(); i++) {
            if (priorityArray.get(i) == priorityLow) {
                calcOrder.set(i, counter);
                counter++;
            }
        }

        return calcOrder;
    }

    /**
     * 与えられた文字列の最後尾の(のIndexと、この位置から初めに見つかった)の位置までのIndexを
     * Parenthesisオブジェクトとして返却する
     *
     * @param calcText 与えられた文字列
     * @return
     */
    public Parenthesis lastOncePare(String calcText) {
        Pattern leftPare = Pattern.compile("\\(");
        Pattern rightPare = Pattern.compile("\\)");
        Matcher leftPareMatcher = leftPare.matcher(calcText);
        Matcher rightPareMatcher = rightPare.matcher(calcText);
        Integer startPosition = null;

        while (leftPareMatcher.find()) {
            startPosition = leftPareMatcher.start();
        }
        Integer endPosition = null;
        if (startPosition != null) {
            rightPareMatcher.find(startPosition);
            endPosition = rightPareMatcher.start();
        }

        return new Parenthesis(startPosition, endPosition);
    }

    /**
     * 文字列に(が存在するかチェックし、存在する場合はtrueを、存在しない場合は
     * falseを返却する
     *
     * @param text
     * @return
     */
    public boolean existLeftPare(String text) {
        Pattern leftPare = Pattern.compile("\\(");
        Matcher matcher = leftPare.matcher(text);
        return matcher.find();
    }

}
