import java.util.*;

/**
 * 「結果」として複素数を表すクラス。
 * 実部と虚部はそれぞれ{@code double}で保持される。
 * 加算などの基本的な演算や、文字列から複素数を生成する機能を持つ。
 */
class Complex {
    /**
     * 複素数の実部
     */
    public double real;
    /**
     * 複素数の虚部
     */
    public double imag;
    /**
     * 実部と虚部がともに0の複素数を生成するコンストラクタ。
     */
    Complex (){
        real = 0;
        imag = 0;
    }
    /**
     * 複素数{@code re}+i{@code im}の複素数を生成するコンストラクタ。
     * @param re 実部
     * @param im 虚部
     */
    Complex (double re, double im){
        real = re;
        imag = im;
    }
    /**
     * 自身の実部に値を設定するメソッド。
     * @param re 設定する実部の値
     */
    public void setReal (double re){
        real = re;
    }
    /**
     * 自身の実部を返すメソッド。
     * @return 自身の実部の値
     */
    public double getReal (){
        return real;
    }
    /**
     * 自身の虚部に値を設定するメソッド。
     * @param im 設定する虚部の値
     */
    public void setImag (double im){
        imag = im;
    }
    /**
     * 自身の虚部を返すメソッド。
     * @return 自身の虚部の値
     */
    public double getImag (){
        return imag;
    }
    /**
     * 自身の共役複素数を返すメソッド。
     * @return 自身の共役複素数
     */
    public Complex getConjugate (){
        return new Complex(real, -1*imag);
    }
    /**
     * その複素数の大きさを返すメソッド。
     * @return 自身の複素数の大きさ
     */
    public double getAbs (){
        Double s = (real * real) + (imag * imag);
        return Math.sqrt(s);
    }
    /**
     * その複素数の偏角を返すメソッド。 
     * @return 自身の複素数の偏角の大きさ
     */
    public double getArg (){
        Double arg = Math.atan2(imag,real);
        return arg;
    }
    /**
     * 複素数を表す文字列を返す。
     * 3通りの場合に合わせて文字列を生成する。<p>
     * <blockquote>
     * 変換する複素数が<pre>{@code 2.0+3.0*i}</pre>の場合、{@code 2.0+3.0i}という文字列が返る。
     * <pre>{@code 4.0+0.0*i}</pre>の場合、{@code 4.0}という文字列が返る。
     * <pre>{@code 0.0+5.0*i}</pre>の場合、{@code 5.0i}という文字列が返る。
     * </blockquote><p>
     */
    public String toString(){
        String r = String.format("%1$4.2f", real);
        String is = String.format("%1$4.2f", imag);
        if(real != 0){      // 実部があるとき
            if(imag > 0){   // 虚部が正数のとき
                return r + "+" + is + "i";
            }else if(imag < 0){     // 虚部が負数のとき
                return r + is + "i";
            }else{      // 虚部がないとき
                return r;
            }
        }else{      // 実部がないとき
            if(imag != 0){      // 虚部のみのとき
                return is + "i";
            }else{      // 値が0のとき、実部の0のみを返す。
                return r;
            }
        }
    }
    /**
     * 与えられた複素数と自身との加算結果を表す複素数を返す。
     * @param comp　加算する複素数
     * @return 加算結果{@code this}+{@code comp}の結果である複素数
     */
    public Complex add (Complex comp){
        return new Complex(real + comp.getReal(), imag + comp.getImag());
    }
    /**
     * 与えられた複素数と自身との減算結果を表す複素数を返す。
     * @param comp 減算する複素数
     * @return 減算結果{@code this}-{@code comp}の結果である複素数
     */
    public Complex sub (Complex comp){
        return new Complex(real - comp.getReal(), imag - comp.getImag());
    }
    /**
     * 与えられた複素数と自身との乗算結果を表す複素数を返す。
     * @param comp 乗算する複素数
     * @return 乗算結果{@code this}*{@code comp}の結果である複素数
     */
    public Complex mul(Complex comp){
        Complex p = new Complex();

        p.setReal((real * comp.getReal()) - (imag * comp.getImag()));
        p.setImag((imag * comp.getReal()) - (real * comp.getImag()));
        return p;
    }
    /**
     * 与えられた複素数と自身との除算結果を表す複素数を返す。なお{@code comp}の実部と虚部のどちらも0のときは{@code null}を返す。
     * @param comp 除算する複素数
     * @return 除算結果{@code this}/{@code comp}の結果である複素数
     */
    public Complex div (Complex comp){
        if(comp.real == 0 && comp.imag == 0) return null;
        Complex p = new Complex();

        p.setReal(((real * comp.getReal()) + (imag * comp.getImag())) / ((comp.real *comp.getReal()) + (comp.imag * comp.getImag())));
        p.setImag(((imag * comp.real) - (real * comp.imag)) / ((comp.real * comp.real) + (comp.imag * comp.imag)));
        return p;
    }
    /**
     * 与えられた「ブロック」から複素数または実数、虚数を生成する。虚数のみの場合、{@code 2.2i}のように後ろに{@code i}を付け足す。
     * @param block 電卓から受け取る「ブロック」
     * @return 複素数(場合により実数または虚数)
     */
    public static Complex read(final List<String> block){
        try{
            Complex ret = new Complex();
            
            StringTokenizer st = new StringTokenizer(block.get(1));     //block一つ目はコマンドのみなので無視
            ArrayList<String> vs = new ArrayList<String>();
            while(st.hasMoreTokens()) vs.add(st.nextToken());
            String [] ts = vs.toArray(new String[vs.size()]);
        
            if(ts.length == 1){     // 実部か虚部のどちらかのみが入力されたとき
                if(ts[0].contains("i")){    // 虚部の場合
                    int index = ts[0].indexOf("i");
                    String sts = ts[0].substring(0,index);  // iを取り除いた文字列
                
                    ret.setImag(Double.parseDouble(sts));
                    return ret;
                }else{
                    ret.setReal(Double.parseDouble(ts[0]));
                    return ret;
                }
            }else if(ts.length == 2){     // 複素数が入力された場合
                ret.setReal(Double.parseDouble(ts[0]));
                ret.setImag(Double.parseDouble(ts[1]));
            }            
            return ret;
        } catch(Exception e) {
            return null;
        }
    }
}