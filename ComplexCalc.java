/**
 * 「結果」が複素数であるクラス。実部または虚部のみを扱うことも出来る。
 * 複素数クラスを定義し、演算として加減乗除ができる。
 * またその複素数の共役複素数や大きさ、偏位を求めることができる　ようにする。
 * コンパイルと実行
 * javac Calculator.java ComplexCalc.java MemoCalc.java IntCalc.java
 * java ComplexCalc
 */

import java.util.*;
import java.io.*;

/**
 * 新たな複素数を入力するコマンド。入力された値を複素数表示にして返す。<p>
 * <blockquote>例えば入力が<pre>{@code 
 * com :
 *  TAB 1.0 2.0}</pre>のとき、 {@code 1.0+2.0i} を表す。</blockquote><p>
 * <blockquote>入力が<pre>{@code 
 * com :
 *  TAB 1.0i}</pre>のみのとき、{@code 2.0i}を表す。</blockquote>
 */
class ComplexValue implements Command<Complex> {
    public Complex tryExec (final String [] ts, final List<String> block, final Complex res){
        if(block.size() <= 1) return null;
        if(ts.length == 1 && "com".equals(ts[0])){
            return Complex.read(block);
        }
        return null;
    }
}

/**
 * 加算の「コマンド」。<p>
 * <blockquote><pre>{@code 
 *add :
  TAB 1.0 2.0}</pre>という「ブロック」を受け付けたとき、 {@code 1.0+2.0i} を現在の「結果」に足した「結果」を返す。<p>
 * <pre>{@code 
 * add :
 *  TAB 1.0}</pre>という「ブロック」 を受け付けたとき、{@code 1.0} を現在の「結果」に足した「結果」を返す。<p>
 * <pre>{@code 
 * add :
 *  TAB 2.0i}</pre>という「ブロック」を受け付けたとき、{@code 2.0i} を現在の「結果」に足した「結果」を返す。
 * </blockquote><br/>
 * もしくは {@code add} の後ろに変数名を書いたブロックを受け付けて、変数に保存された複素数を足した「結果」を返す。
 */
class ComplexAdd extends CommandWithMemory<Complex> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ。
     * @param mem 変数の情報を保持するオブジェクト
     */
    ComplexAdd(Memory<Complex> mem) {
        super(mem);
    }

    public Complex tryExec (final String [] ts, final List<String> block, final Complex res){
        // 複素数の値が入力された場合
        if(block.size() > 1 && ts.length == 1 && "add".equals(ts[0])){
            Complex v = Complex.read(block);
            return res.add(v);
        }       // 複素数を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "add".equals(ts[0])){
            Complex v = mem.get(ts[1]);
            return res.add(v);
        }
        return null;
    }
}

/**
 * 減算の「コマンド」。<p>
 * <blockquote><pre>{@code 
 *sub :
  TAB 1.0 2.0}</pre>という「ブロック」を受け付けたとき、 {@code 1.0+2.0i} を現在の「結果」から引いた「結果」を返す。<p>
 * <pre>{@code 
 * sub :
 *  TAB 1.0}</pre>という「ブロック」 を受け付けたとき、{@code 1.0} を現在の「結果」から引いた「結果」を返す。<p>
 * <pre>{@code 
 * sub :
 *  TAB 2.0i}</pre>という「ブロック」を受け付けたとき、{@code 2.0i} を現在の「結果」から引いた「結果」を返す。
 * </blockquote><br/>
 * もしくは {@code sub} の後ろに変数名を書いたブロックを受け付けて、変数に保存された複素数を引いた「結果」を返す。
 */
class ComplexSub extends CommandWithMemory<Complex> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ。
     * @param mem 変数の情報を保持するオブジェクト
     */
    ComplexSub(Memory<Complex> mem) {
        super(mem);
    }

    public Complex tryExec (final String [] ts, final List<String> block, final Complex res){
        // 複素数の値が入力された場合
        if(block.size() > 1 && ts.length == 1 && "sub".equals(ts[0])){
            Complex v = Complex.read(block);
            return res.sub(v);
        }       // 複素数を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "sub".equals(ts[0])){
            Complex v = mem.get(ts[1]);
            return res.sub(v);
        }
        return null;
    }
}

/**
 * 乗算の「コマンド」。<p>
 * <blockquote><pre>{@code 
 *mul :
  TAB 1.0 2.0}</pre>という「ブロック」を受け付けたとき、 {@code 1.0+2.0i} を現在の「結果」に掛けた「結果」を返す。<p>
 * <pre>{@code 
 * mul :
 *  TAB 1.0}</pre>という「ブロック」 を受け付けたとき、{@code 1.0} を現在の「結果」に掛けた「結果」を返す。<p>
 * <pre>{@code 
 * mul :
 *  TAB 2.0i}</pre>という「ブロック」を受け付けたとき、{@code 2.0i} を現在の「結果」に掛けた「結果」を返す。
 * </blockquote><br/>
 * もしくは {@code mul} の後ろに変数名を書いたブロックを受け付けて、変数に保存された複素数を掛けた「結果」を返す。
 */
class ComplexMul extends CommandWithMemory<Complex> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ。
     * @param mem 変数の情報を保持するオブジェクト
     */
    ComplexMul(Memory<Complex> mem) {
        super(mem);
    }

    public Complex tryExec (final String [] ts, final List<String> block, final Complex res){
        // 複素数の値が入力された場合
        if(block.size() > 1 && ts.length == 1 && "mul".equals(ts[0])){
            Complex v = Complex.read(block);
            return res.mul(v);
        }       // 複素数を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "mul".equals(ts[0])){
            Complex v = mem.get(ts[1]);
            return res.mul(v);
        }
        return null;
    }
}

/**
 * 除算の「コマンド」。<p>
 * <blockquote><pre>{@code 
 *div :
  TAB 1.0 2.0}</pre>という「ブロック」を受け付けたとき、 {@code 1.0+2.0i} で現在の「結果」を割った「結果」を返す。<p>
 * <pre>{@code 
 * div :
 *  TAB 1.0}</pre>という「ブロック」 を受け付けたとき、{@code 1.0} で現在の「結果」を割った「結果」を返す。<p>
 * <pre>{@code 
 * div :
 *  TAB 2.0i}</pre>という「ブロック」を受け付けたとき、{@code 2.0i} で現在の「結果」を割った「結果」を返す。
 * </blockquote><br/>
 * もしくは {@code div} の後ろに変数名を書いたブロックを受け付けて、変数に保存された複素数で割った「結果」を返す。
 */
class ComplexDiv extends CommandWithMemory<Complex> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ。
     * @param mem 変数の情報を保持するオブジェクト
     */
    ComplexDiv(Memory<Complex> mem) {
        super(mem);
    }

    public Complex tryExec (final String [] ts, final List<String> block, final Complex res){
        // 複素数の値が入力された場合
        if(block.size() > 1 && ts.length == 1 && "div".equals(ts[0])){
            Complex v = Complex.read(block);
            return res.div(v);
        }       // 複素数を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "div".equals(ts[0])){
            Complex v = mem.get(ts[1]);
            return res.div(v);
        }
        return null;
    }
}

/**
 * 共役複素数を返す「コマンド」。<p>
 * <blockquote>
 * <pre>{@code conj}</pre>という入力で、現在の「結果」をその共役複素数に更新する。<p>
 * もしくは {@code conj} の後ろに変数名を書いたブロックを受け付けて、変数に保存された複素数の共役複素数を返す。
 * </blockquote>
 */
class CompConju extends CommandWithMemory<Complex> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ。
     * @param mem 変数の情報を保持するオブジェクト
     */
    CompConju(Memory<Complex> mem){
        super(mem);
    }

    public Complex tryExec (final String [] ts, final List<String> block, final Complex res){
        if(ts.length == 1 && "conj".equals(ts[0])){
            return res.getConjugate();
        }       // 複素数を保存した変数が指定された場合
        else if(ts.length == 2 && "conj".equals(ts[0])){
            Complex v = mem.get(ts[1]);
            return v.getConjugate();
        }
        return null;
    }
}

/**
 * 複素数の大きさを比較する「コマンド」。
 * <blockquote>
 * <pre>{@code 
 * comp :
 *  TAB 1.0 2.0}</pre>という入力で、現在の「結果」と {@code1.0+2.0i} との大きさを比較し、大きい方の複素数が「結果」として出力される。<p>
 * もしくは {@code conp} の後ろに変数名を書いたブロックを受け付けて、変数に保存された複素数と現在の「結果」との比較をする。<p>
 * 大きさが等しい場合{@code 1.0}が「結果」となる。
 * </blockquote>
 */
class CompCompare extends CommandWithMemory<Complex> {
    /**
     * 変数の情報を保持する {@code Memory} オブジェクトを受け取るコンストラクタ。
     * @param mem 変数の情報を保持するオブジェクト
     */
    CompCompare(Memory<Complex> mem){
        super(mem);
    }

    public Complex tryExec (final String [] ts, final List<String> block, final Complex res){
        Complex e = new Complex(1,0);
        // 複素数を保存した変数が指定された場合
        if(block.size() == 1 && ts.length == 2 && "comp".equals(ts[0])){
            Complex v = mem.get(ts[1]);
            Double rd = res.getAbs();
            Double vd = v.getAbs();

            if(rd < vd){
                return v;
            }else if(rd > vd){
                return res;
            }else{
                return e;
            }
        }
        if(block.size() > 1 && ts.length == 1 && "comp".equals(ts[0])){
            Complex v = Complex.read(block);

            Double rd = res.getAbs();
            Double vd = v.getAbs();

            if(rd < vd){
                return v;
            }else if(rd > vd){
                return res;
            }else{
                return e;
            }
        }
        return null;
    }
}

/**
 * 複素数電卓を作成して動作させるクラス. 
 * ターミナルで次のような実行ができる. 
 * <p><blockquote><pre>{@code
 * $ javac Calculator.java IntCalc.java MemoCalc.java ComplexCalc.java Complex.java
 * $ java ComplexCalc
 * 0.0
 * >> com :
 * ..      1 2
 * ..
 * 1.0+2.0i
 * >> store x
 * 1.0+2.0i
 * >> add :
 * ..      3.3 4.4
 * ..
 * 4.3+6.4i
 * >> add x
 * 5.3+8.4i
 * >> conj
 * 5.3-8.4i
 * >>
 * }</pre></blockquote><p>
 * これは, 最初に「結果」が 0.0電卓が動き始め, 
 * まずは最初のプロンプトの後ろで {@code com : } と打って複数行の「ブロック」の入力を始め, 
 * 続く TAB 始まりの 複素数の実部と虚部を書き, 続く空行で「ブロック」の入力を終え, 
 * その「ブロック」に対して {@code ComplexValue} が実行されて「結果」がその行列になり, 
 * 続いて {@code store x} と入力し, 
 * {@code LoadStore} が動作してその行列が変数 x に保存され, 
 * 続いて {@code add :} からの数行で同様に複素数を入力し, 
 * それに対して {@code ComplexAdd} が動作して「結果」が行列の和になり, 
 * さらに {@cdoe add x} と入力し, 
 * それに対して {@code ComplexAdd} が動作して変数 x に保存した行列が加算され, 
 * 最後に {@code conj} と入力し, 
 * それに対して {@code CompConju} が動いて「結果」の共役複素数が新しい「結果」なった. 
 */
public class ComplexCalc {
    public static void main(String[] args) throws Exception {
        Memory<Complex> mem = new Memory<Complex>();
        ArrayList<Command<Complex>> comms = new ArrayList<Command<Complex>>();
        comms.add(new EmptyCommand<Complex>());
        comms.add(new ComplexAdd(mem));
        comms.add(new ComplexSub(mem));
        comms.add(new ComplexMul(mem));
        comms.add(new ComplexDiv(mem));
        comms.add(new CompConju(mem));
        comms.add(new CompCompare(mem));
        comms.add(new LoadStore<Complex>(mem));
        comms.add(mem);
        comms.add(new ComplexValue());

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Calculator<Complex> c = new Calculator<Complex>(br, comms);
        c.run(new Complex());
    }
}