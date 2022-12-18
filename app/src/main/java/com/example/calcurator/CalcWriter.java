package com.example.calcurator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalcWriter {

    // 0除算と)の追加処理
    // リファクタ 数値・.・()・演算子ようメソッドに分割

    private Pattern numOrLeftPare = Pattern.compile("[0-9\\(]");
    private Pattern numOrRightPare = Pattern.compile("[0-9\\)]");
    private Pattern num = Pattern.compile("[0-9]");
    private Pattern numOfZero = Pattern.compile("0");
    private Pattern operator = Pattern.compile("[\\+\\-\\%÷×]");
    private Pattern rightPare = Pattern.compile("\\)");
    private Pattern leftPare = Pattern.compile("\\(");
    private Pattern decimalPoint = Pattern.compile("\\.");
    private Pattern space = Pattern.compile("\\s");
    private Pattern minusOperator = Pattern.compile("\\-");

    private Pattern spaceOrLeftPare = Pattern.compile("[\\s\\(]"); // \\s,\\(

    private Calculator calculator = new Calculator();

    public String writeCalc(String calcText, String writeText) {
        String returnText = calcText;
        boolean isEmptyText = calcText.isEmpty();

        // 元になるテキストに何も書かれていない場合の処理
        if (isEmptyText) {
            Matcher matcher = numOrLeftPare.matcher(writeText);
            // 書き込む値が数値または(だった場合
            if (matcher.matches()) {
                return writeText;
            }
            return returnText;
        } else {
            // 元になるテキストに何か値が入っていた場合の処理
            int endIndex = calcText.length() - 1; // 末尾位置
            String endVal = String.valueOf(calcText.charAt(endIndex)); // 末尾の文字

            Matcher numOfZeroMatcher = numOfZero.matcher(endVal);
            // 元になる文字列の最後尾が0の場合
            if(numOfZeroMatcher.matches()){
                Matcher writeNumOfLeftPareMatcher = numOrLeftPare.matcher(writeText);
                // 元文字列が1文字の場合、かつ書き込む値が数値または(の場合
                if (endIndex == 0 && writeNumOfLeftPareMatcher.matches()){
                    return writeText;
                }

                Matcher writeNumMatcher = num.matcher(writeText);
                boolean startWithZero = startWithZeroCheck(calcText);
                // かつ書き込む値が数値かつ0で始まっている場合の場合
                if (writeNumMatcher.matches() && startWithZero){
                    return returnText.substring(0, returnText.length() - 1) + writeText;
                }

                Matcher decimalPointMatcher = decimalPoint.matcher(endVal);
                boolean alreadyExistDecimalPoint = alreadyExistDecimalPoint(calcText);
                // かつ書き込む値が.の場合
                if (decimalPointMatcher.matches() && !alreadyExistDecimalPoint){
                    return returnText + writeText;
                }
            }

            Matcher numberMatcher = num.matcher(endVal);
            // 元になる文字列の最後尾が数値の場合
            if (numberMatcher.matches()) {
                Matcher writeNumMatcher = num.matcher(writeText);
                // かつ、書き込む値が数値の場合
                if (writeNumMatcher.matches()){
                    return calcText + writeText;
                }
                Matcher writeOperatorMatcher = operator.matcher(writeText);
                // かつ、書き込む値が演算子の場合
                if(writeOperatorMatcher.matches()){
                    return calcText + " " + writeText + " ";
                }

                Matcher writeDecimalPointMatcher = decimalPoint.matcher(writeText);
                boolean alreadyExistDecimalPoint = alreadyExistDecimalPoint(calcText);
                // かつ書き込む値が.の場合
                if (writeDecimalPointMatcher.matches() && !alreadyExistDecimalPoint){
                    return returnText + writeText;
                }

                Matcher rightPareMatcher = rightPare.matcher(writeText);
                boolean canPlace = rightParenthesisCanPlace(calcText);
                // かつ書き込む値が)かつ配置可能な場合
                if(rightPareMatcher.matches() && canPlace){
                    return returnText + writeText;
                }
            }

            Matcher spaceMatcher = space.matcher(endVal);
            // 元になる文字列の最後尾が空白(演算子)の場合
            if (spaceMatcher.matches()){
                Matcher writeNumOrLeftPareMatcher = numOrLeftPare.matcher(writeText);
                // かつ、書き込む値が数値または(の場合
                if (writeNumOrLeftPareMatcher.matches()){
                    return calcText + writeText;
                }
                Matcher writeOperatorMatcher = operator.matcher(writeText);
                // かつ、書き込む値が演算子の場合
                if(writeOperatorMatcher.matches()){
                    return calcText.substring(0, calcText.length() - 2) + writeText + " ";
                }
            }

            Matcher leftPareMatcher = leftPare.matcher(endVal);
            // 元になる文字列の最後尾が(の場合
            if (leftPareMatcher.matches()){
                Matcher writeNumOrLeftPareMatcher = numOrLeftPare.matcher(writeText);
                // かつ、書き込む値が数値または(の場合
                if (writeNumOrLeftPareMatcher.matches()){
                    return calcText + writeText;
                }
                Matcher writeMinusOperatorMatcher = minusOperator.matcher(writeText);
                // かつ、書き込む値が-演算子の場合
                if(writeMinusOperatorMatcher.matches()){
                    return calcText + writeText;
                }
            }

            Matcher minusOperatorMatcher = minusOperator.matcher(endVal);
            // 元になる文字列の最後尾が-の場合
            if(minusOperatorMatcher.matches()){
                Matcher numMatcher = num.matcher(writeText);
                // かつ、書き込む値が数値の場合
                if(numMatcher.matches()){
                    return calcText + writeText;
                }
            }

            Matcher decimalPointMatcher = decimalPoint.matcher(endVal);
            // 元になる文字列の最後尾が.の場合
            if(decimalPointMatcher.matches()){
                Matcher numMatcher = num.matcher(writeText);
                // かつ、書き込む値が数値の場合
                if(numMatcher.matches()){
                    return calcText + writeText;
                }
            }

            Matcher rightPareMatcher = rightPare.matcher(endVal);
            // 元になる文字列の最後尾が)の場合
            if(rightPareMatcher.matches()){
                Matcher rightParenthesisMatcher = rightPare.matcher(writeText);
                boolean canPraise = rightParenthesisCanPlace(calcText);
                // かつ、書き込む値が)の場合
                if(rightParenthesisMatcher.matches() && canPraise){
                    return calcText + writeText;
                }
                Matcher operatorMatcher = operator.matcher(writeText);
                // かつ、書き込む値が演算子の場合
                if (operatorMatcher.matches()){
                    return calcText + " " + writeText + " ";
                }
            }

        }
        return returnText;
    }

    /**
     * 数値を挿入する処理 0-9
     * @param calcText
     * @param numString
     * @return
     */
    public String writeNumber(String calcText, String numString){
        Matcher matcher = num.matcher(numString);

        if (calcText.isEmpty() && matcher.matches()) {
            // 元になるテキストに何も書かれていない、かつ書き込む値が数値の場合の処理
            return numString;
        }else if(matcher.matches()){
            // 元になるテキストに何か値が入っていた場合の処理
            String endVal = calcText.substring(calcText.length() - 1); // 末尾の文字
            Matcher numOfZeroMatcher = numOfZero.matcher(endVal);
            Matcher rightPareMatcher = rightPare.matcher(endVal);

            if(numOfZeroMatcher.matches() && startWithZeroCheck(calcText)){
                // 末尾文字が0かつ数値列が0から始まっている場合
                return calcText.substring(0, calcText.length() - 1) + numString;
            }else if (!rightPareMatcher.matches()){
                // 文字列終端が)以外の場合
                return calcText + numString;
            }
        }
        // その他は挿入不可のためそのまま返却
        return calcText;
    }

    /**
     * 演算子を挿入する処理 ×÷%-+
     * @param calcText
     * @param opString
     * @return
     */
    public String writeOperator(String calcText, String opString){
        Matcher opMatcher = operator.matcher(opString);
        if (!calcText.isEmpty() && opMatcher.matches()){
            // 元の文字列が空ではなく、かつ書き込む値が演算子の場合
            String endVal = calcText.substring(calcText.length() - 1); // 終端文字
            Matcher numOrRightPareMatcher = numOrRightPare.matcher(endVal);
            Matcher leftPareMatcher = leftPare.matcher(endVal);
            Matcher minusMatcher = minusOperator.matcher(opString);
            Matcher spaceMatcher = space.matcher(endVal);

            if(numOrRightPareMatcher.matches()){
                // 文字列終端が数値及び)の場合
                return calcText + " " + opString + " ";
            }else if (leftPareMatcher.matches() && minusMatcher.matches()){
                // 文字列終端が(かつ書き込みたい値が-の場合
                return calcText + opString;
            }else if(spaceMatcher.matches()){
                // 文字列終端が空白(演算子)の場合の処理
                return calcText.substring(0, calcText.length() - 2) + opString + " ";
            }
        }
        // それ以外は書き込めない為何もしないで返却
        return calcText;
    }

    /**
     * 渡された文字列に.(小数点)を加えて返却するメソッド文字列末尾が数値以外の場合と
     * 数値文字列に既に.が記載されていた場合は何もせず返却する
     *
     * @param calcText
     * @return
     */
    public String writeDecimalPoint(String calcText){
        if (!calcText.isEmpty()){
            // 元の文字列が空でない場合の処理
            String endVal = calcText.substring(calcText.length() - 1); // 文字列末尾の文字
            Matcher numMatcher = num.matcher(endVal);
            if(numMatcher.matches()){
                // 最後尾の文字が数値の場合の処理
                // 数値の文字列の並びに.が記載されているか
                boolean alreadyExistDp = alreadyExistDecimalPoint(calcText);
                if (!alreadyExistDp){
                    return calcText + ".";
                }
            }
        }
        return calcText;
    }

    /**
     * 渡された文字列に(を追加して返却する。追加不可の場合はそのまま返却する
     * @param calcText
     * @return
     */
    public String writeLeftParenthesis(String calcText){
        if (calcText.isEmpty()){
            // 文字列が空の場合の処理
            return "(";
        }else{
            String endVal = calcText.substring(calcText.length() - 1);
            Matcher spaceOrLeftPareMatcher = spaceOrLeftPare.matcher(endVal);
            if (spaceOrLeftPareMatcher.matches()){
                // 文字列の末尾が(か空白(演算子)の場合の処理
                return calcText + "(";
            }
        }
        // それ以外は書き込みできない為そのまま返却
        return calcText;
    }

    /**
     * 渡された文字列に)を追加して返却する。追加不可の場合はそのまま返却する
     * @param calcText
     * @return
     */
    public String writeRightParenthesis(String calcText){
        if (!calcText.isEmpty()){
            // 文字列が空でない場合の処理
            String endVal = calcText.substring(calcText.length() - 1); // 文字列がの末尾
            boolean rightPareCanPlace = rightParenthesisCanPlace(calcText); // )が配置可能か
            Matcher numOrRightPareMatcher = numOrRightPare.matcher(endVal);

            if(rightPareCanPlace && numOrRightPareMatcher.matches()){
                // )が配置可能であった場合の処理
                return calcText + ")";
            }
        }
        // 配置可能でない場合はそのまま返却する
        return calcText;
    }

    /**
     * 渡された文字列式を計算し返却する。文字列が計算可能な状態か判定し、計算できる状態であれば
     * 計算を行い答えを返却し、可能で無ければ渡された文字列をそのまま返却する
     *
     * @param calcText 計算させたい文字列の式 example "1 + 2 * (3 + 4)"
     * @return
     */
    public String writeAns(String calcText){
        if(calcText.length() < 1){
            return calcText;
        }
//        // 渡された式が計算可能かのチェック
//        int leftPareCount = 0; // 左(カウンタ
//        int rightPareCount = 0; // 右)カウンタ
//        String terminateStr = calcText.substring(calcText.length() - 1); // 終端文字
//
//        Matcher leftPareMatcher = leftPare.matcher(calcText);
//        Matcher rightPareMatcher = rightPare.matcher(calcText);
//        while(leftPareMatcher.find()){
//            leftPareCount++;
//        }
//        while(rightPareMatcher.find()){
//            rightPareCount++;
//        }
//
//        Matcher matcher = numOrRightPare.matcher(terminateStr);

        // 処理できるかのフラグ
        boolean canWriteAns = canCalculate(calcText);
        // java.lang.ArithmeticException: Division by zero
        String result;
        // 0除算が行われた場合はErrorの文字列で返却する
        try{
            result = canWriteAns ? calculator.calc(calcText) :calcText;
        }catch (ArithmeticException ex){
            ex.printStackTrace();
            result = "Error";
        }
        return result;
    }

    /**
     * 文字列式が計算可能かを精査し、計算可能であればtureを不可な状態ならfalseを返却する
     * 処理としては()の数合わせと終端が数値で終わっているかの単純な処理なのでその点を注意
     * @param calcText
     * @return
     */
    public boolean canCalculate(String calcText){
        // 文字列が空の場合は処理不可能
        if(calcText.length() < 1){
            return false;
        }
        // 渡された式が計算可能かのチェック
        int leftPareCount = 0; // 左(カウンタ
        int rightPareCount = 0; // 右)カウンタ
        String terminateStr = calcText.substring(calcText.length() - 1); // 終端文字

        Matcher leftPareMatcher = leftPare.matcher(calcText);
        Matcher rightPareMatcher = rightPare.matcher(calcText);
        while(leftPareMatcher.find()){
            leftPareCount++;
        }
        while(rightPareMatcher.find()){
            rightPareCount++;
        }

        Matcher matcher = numOrRightPare.matcher(terminateStr);

        // 左右のパレンティスの値が同じかつ文字列終端が数値で終了している
        return leftPareCount == rightPareCount && matcher.matches();
    }

    /**
     * 文字列末尾を1つ削除する
     *
     * @param calcText 元になる文字列
     * @return
     */
    public String deleteTail(String calcText) {
        boolean isEmptyText = calcText.isEmpty();
        if (isEmptyText) {
            return calcText;
        } else {
            int endIndex = calcText.length() - 1; // 末尾位置
            String endVal = String.valueOf(calcText.charAt(endIndex)); // 末尾の文字
            Matcher spaceMatcher = space.matcher(endVal);
            if(spaceMatcher.matches()){
                // 末尾が空白の場合(演算子)
                return calcText.substring(0, calcText.length() - 3);
            }else{
                // 末尾が数値または().記号
                return calcText.substring(0, calcText.length() - 1);
            }
        }
    }

    /**
     * 文字列後方が0で終始しているかチェックする
     * @param calcText
     * @return
     */
    private boolean startWithZeroCheck(String calcText){
        Pattern pattern = Pattern.compile("[0-9\\.]");
        Pattern endPattern = Pattern.compile("[^\\d\\.]");
        boolean startWithZero = true;
        int length = calcText.length();
        // 後方から前方に精査、なお後方1文字前は0前提なので、その後ろから精査している
        for (int i = length - 2; i >= 0; i--) {
            String val = String.valueOf(calcText.charAt(i));
            Matcher matcher = pattern.matcher(val);
            Matcher endMatcher = endPattern.matcher(val);
            // 数値か.記号以外でbreak
            if(endMatcher.matches()){
                break;
            }
            // 数値か.が入っていたらstartWithZeroをfalseで書き換えbreak
            if (matcher.matches()){
                startWithZero = false;
                break;
            }
        }
        return startWithZero;
    }

    /**
     * 与えられた文字列の値から後方に.記号があるか無いかをチェックする後方に数値以外が出現した時点で処理を返却
     * example1 "1 + 2 + 3" -> return false
     * example2 "1 + 2 + 3.0" -> return true
     * example2 "1 + 2.0 + 3" -> return false
     * @param calcText
     * @return
     */
    private boolean alreadyExistDecimalPoint(String calcText){
        int length = calcText.length();
        boolean alreadyExistDecimalPoint = false;
        Pattern endPattern = Pattern.compile("[\\D]");
        Pattern decimalPointPattern = Pattern.compile("\\.");
        // 文字列の後方の2文字前から精査する
        for (int i = length - 2; i >= 0; i--){
            String val = String.valueOf(calcText.charAt(i));
            Matcher decimalPointMatcher = decimalPointPattern.matcher(val);
            if(decimalPointMatcher.matches()){
                alreadyExistDecimalPoint = true;
                break;
            }
            Matcher endMatcher = endPattern.matcher(val);
            if(endMatcher.matches()){
                break;
            }
        }
        return alreadyExistDecimalPoint;
    }

    /**
     * 引数の文字列情報から)が配置できるか精査する
     * example1 "(1 + 2" -> return true
     * example2 "1 + 2" -> return false
     * example3 "1 + (2 + (3 + 4)" -> return true
     * @param calcText
     * @return
     */
    private boolean rightParenthesisCanPlace(String calcText){
        int length = calcText.length();
        int leftParenthesisCount = 0;
        int rightParenthesisCount = 0;
        Pattern leftParePattern = Pattern.compile("\\(");
        Pattern rightParePattern = Pattern.compile("\\)");

        for (int i = length - 1; i >= 0; i--) {
            String val = String.valueOf(calcText.charAt(i));
            Matcher leftPareMatcher = leftParePattern.matcher(val);
            if (leftPareMatcher.matches()){
                leftParenthesisCount++;
            }
            Matcher rightPareMatcher = rightParePattern.matcher(val);
            if(rightPareMatcher.matches()){
                rightParenthesisCount++;
            }
        }
        return leftParenthesisCount > rightParenthesisCount;
    }

}
