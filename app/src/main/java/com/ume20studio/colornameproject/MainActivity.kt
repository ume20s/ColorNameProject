package com.ume20studio.colornameproject

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    // 収録色数
    private val colNUM: Int = 11404

    // 色データ配列
    private var c = arrayOf<ColorData>()

    // 画面パーツオブジェクト
    private lateinit var seekBarRed: SeekBar
    private lateinit var seekBarGreen: SeekBar
    private lateinit var seekBarBlue: SeekBar
    private lateinit var seekBarKyoyou: SeekBar
    private lateinit var textRed: TextView
    private lateinit var textGreen: TextView
    private lateinit var textBlue: TextView
    private lateinit var textKyoyou: TextView

    // RGB値
    var r: Int = 127
    var g: Int = 127
    var b: Int = 127

    // 許容誤差
    var kyoyou: Int = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        // もとからある初期化
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 色名リソースの読み込み
        val rYomi = resources.getStringArray(R.array.tsvYomi)
        val rKaki = resources.getStringArray(R.array.tsvKaki)
        val rRed = resources.getStringArray(R.array.tsvRed)
        val rGreen = resources.getStringArray(R.array.tsvGreen)
        val rBlue = resources.getStringArray(R.array.tsvBlue)
        for (i in 0 until colNUM) {
            val cTmp = ColorData(rYomi[i], rKaki[i], rRed[i].toInt(), rGreen[i].toInt(), rBlue[i].toInt())
            c += cTmp
        }

        // 画面パーツオブジェクトの初期化
        seekBarRed = findViewById(R.id.seekBarRed)
        seekBarGreen = findViewById(R.id.seekBarGreen)
        seekBarBlue = findViewById(R.id.seekBarBlue)
        seekBarKyoyou = findViewById(R.id.seekBarKyoyou)
        textRed = findViewById(R.id.textRed)
        textGreen = findViewById(R.id.textGreen)
        textBlue = findViewById(R.id.textBlue)
        textKyoyou = findViewById(R.id.textKyoyou)

        // シークバーと値表示部の初期化
        setupSeekBar()
        setupTextView()

        // カラーコードとカラーパネルの表示
        dispColors()

        // 最初のリザルト表示
        dispItemName()
        dispResult()
    }

    // シークバーの初期化
    private fun setupSeekBar(){
        // シークバーの初期表示
        seekBarRed.progress = r
        seekBarGreen.progress = g
        seekBarBlue.progress = b
        seekBarKyoyou.progress = kyoyou

        // イベントリスナの追加
        seekBarRed.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    // シークバーの値を取得
                    r = progress

                    // 値表示部とカラーパネルの表示
                    textRed.setText(r.toString())
                    dispColors()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                // シークバーを離したらリザルト表示
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    dispResult()
                }
            }
        )
        seekBarGreen.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    // シークバーの値を取得
                    g = progress

                    // 値表示部とカラーパネルの表示
                    textGreen.setText(g.toString())
                    dispColors()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                // シークバーを離したらリザルト表示
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    dispResult()
                }
            }
        )
        seekBarBlue.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    // シークバーの値を取得
                    b = progress

                    // 値表示部とカラーパネルの表示
                    textBlue.setText(b.toString())
                    dispColors()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                // シークバーを離したらリザルト表示
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    dispResult()
                }
            }
        )
        seekBarKyoyou.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    // シークバーの値を取得
                    kyoyou = progress

                    // 値表示部の表示
                    textKyoyou.setText(String.format("%.1f", kyoyou.toFloat() / 10f) + "%")

                    // 許容誤差を変更したら逐次リザルト表示
                    dispResult()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar) {
                }
                override fun onStopTrackingTouch(seekBar: SeekBar) {
                }
            }
        )
    }

    // 値表示部の初期化
    private fun setupTextView() {
        // 値の初期表示
        textRed.setText(r.toString())
        textGreen.setText(g.toString())
        textBlue.setText(b.toString())
    }

    // カラーコードとカラーパネルの表示
    private fun dispColors() {
        val colorCode: String = String.format("%02X", r) + String.format("%02X", g) + String.format("%02X", b)
        findViewById<TextView>(R.id.dispColorCode).setText("code=#$colorCode")
        findViewById<ImageView>(R.id.dispColorArea).setBackgroundColor(Color.parseColor("#FF"+colorCode))
    }

    // 表の項目名の表示
    private fun dispItemName() {
        val r0 = findViewById<View>(R.id.itemName) as ViewGroup
        getLayoutInflater().inflate(R.layout.koumoku, r0)
        val tr0 = r0.getChildAt(0) as TableRow
        ((tr0.getChildAt(0)) as TextView).setText("近似%")
        ((tr0.getChildAt(1)) as TextView).setText("色")
        ((tr0.getChildAt(2)) as TextView).setText("名称")
        ((tr0.getChildAt(3)) as TextView).setText("めいしょう")
        ((tr0.getChildAt(4)) as TextView).setText("R")
        ((tr0.getChildAt(5)) as TextView).setText("G")
        ((tr0.getChildAt(6)) as TextView).setText("B")
        ((tr0.getChildAt(7)) as TextView).setText("#Code")
    }

    // リザルトの表示
    private fun dispResult() {
        var cr = arrayOf<ColorResult>()

        // 許容誤差内の色名データを抽出
        val skyoyou: Int = kyoyou * 765 / 1000
        var cNum: Int = 0
        for(i in 0 until colNUM-1) {
            val gosa: Int = abs(r-c[i].Red) + abs(g-c[i].Green) + abs(b-c[i].Blue)
            if(gosa <= skyoyou) {
                val crTmp = ColorResult(
                    100.0 - gosa.toDouble() * 100.0 / 765.0,
                    c[i].Kaki, c[i].Yomi, c[i].Red, c[i].Green, c[i].Blue,
                    "#" + String.format("%02X", c[i].Red) + String.format("%02X", c[i].Green) + String.format("%02X", c[i].Blue)
                )
                cr += crTmp
                cNum++
            }
            // データが40件以上になったら検索打ち切り
            if(cNum >= 40) break
        }

        // 表をクリーンアップ
        val rn = findViewById<View>(R.id.tableLayout) as ViewGroup
        rn.removeAllViews()

        // 結果の表の表示
        if(cNum == 0) {
            // 検索結果が０件のときのメッセージ
            getLayoutInflater().inflate(R.layout.nodatamessage, rn)
            val tr = rn.getChildAt(0) as TableRow
            ((tr.getChildAt(0)) as TextView).setText(R.string.message_nodata)
        } else {
            // 検索結果を近似％で並べ替え
            cr.sortWith(compareBy{it.Kinji})
            cr.reverse()

            // 検索結果を表にして表示
            for(i in 0..cNum - 1) {
                getLayoutInflater().inflate(R.layout.table, rn)
                val tr = rn.getChildAt(i) as TableRow
                ((tr.getChildAt(0)) as TextView).setText(String.format("%.1f", cr[i].Kinji) + "%")
                ((tr.getChildAt(1)) as TextView).setBackgroundColor(Color.parseColor(cr[i].Code))
                ((tr.getChildAt(2)) as TextView).setText(cr[i].Kaki)
                ((tr.getChildAt(3)) as TextView).setText(cr[i].Yomi)
                ((tr.getChildAt(4)) as TextView).setText(cr[i].Red.toString())
                ((tr.getChildAt(5)) as TextView).setText(cr[i].Green.toString())
                ((tr.getChildAt(6)) as TextView).setText(cr[i].Blue.toString())
                ((tr.getChildAt(7)) as TextView).setText(cr[i].Code)
            }
            // データが40件以上だったら検索を打ち切ったことをお知らせするメッセージ
            if(cNum >= 40) {
                getLayoutInflater().inflate(R.layout.fulldatamessage, rn)
                val tr = rn.getChildAt(cNum) as TableRow
                ((tr.getChildAt(0)) as TextView).setText(R.string.message_fulldata)
            }
        }
    }
}