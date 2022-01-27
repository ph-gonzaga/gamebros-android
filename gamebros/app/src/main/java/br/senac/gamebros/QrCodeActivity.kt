package br.senac.gamebros

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import br.senac.gamebros.databinding.ActivityQrCodeBinding
import br.senac.gamebros.model.Product
import br.senac.gamebros.services.ProductsService
import br.senac.gamebros.utils.Constants
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.ScanMode
import com.google.zxing.BarcodeFormat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import android.provider.Settings
import br.senac.gamebros.model.CartRequest
import br.senac.gamebros.model.CartResponse
import br.senac.gamebros.services.CartsService
import br.senac.gamebros.utils.mostrarToast
import com.google.android.material.snackbar.Snackbar
import retrofit2.converter.gson.GsonConverterFactory

class QrCodeActivity : AppCompatActivity() {
    lateinit var binding: ActivityQrCodeBinding
    lateinit var leitorQr: CodeScanner
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var permissaoConcedida = false
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityQrCodeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        verificarPermissao()

    }
    fun initLeitorQrCode(){
        leitorQr = CodeScanner(this, binding.scannerView)
        leitorQr.camera = CodeScanner.CAMERA_BACK
        leitorQr.formats = listOf(BarcodeFormat.QR_CODE)
        leitorQr.isAutoFocusEnabled = true
        leitorQr.autoFocusMode = AutoFocusMode.SAFE
        leitorQr.scanMode = ScanMode.SINGLE

        leitorQr.setDecodeCallback {
            runOnUiThread {
                val respIntent = Intent()
                respIntent.putExtra("qrcode", it.text)
                setResult(RESULT_OK, respIntent)
                obterProdutoQrCode(respIntent)
                finish()
            }
        }
        leitorQr.setErrorCallback {
            runOnUiThread {
                //mostrarToast(this@QrCodeActivity, "nao foi possivel abrir a câmera")
                Log.e("QrCodeActivity","inicializarLeitorQrCode", it)
                setResult(RESULT_CANCELED)
                finish()
            }
        }
        leitorQr.startPreview()

    }

    fun verificarPermissao(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1)
        }else{
            permissaoConcedida = true
            initLeitorQrCode()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<out String>,grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                permissaoConcedida = true
                initLeitorQrCode()
            }
            else if (!shouldShowRequestPermissionRationale(permissions[0])){
                mostrarDialogoPermissaoCamera()
            }
            else{
                permissaoConcedida = false
                //(this,"É necessário permitir o uso da câmera para utilizar a função de QRCode.")
                setResult(RESULT_CANCELED)
                finish()
            }
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//            val idProduto = data.getStringExtra("qrcode") as String
//            var a = idProduto
//    }

    fun obterProdutoQrCode(data: Intent){
        val idProduto = data.getStringExtra("qrcode") as String
        val service = retrofit.create(ProductsService::class.java)
        val call = service.detalheProduto(idProduto.toInt())
        val callback = object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                val prod = response.body()
                if (response.isSuccessful && prod != null){
                    val request = CartRequest(
                        user_id = 1,
                        product_id = 1
                    )

                    addProduto(request)
                }
            }
            override fun onFailure(call: Call<Product>, t: Throwable) {
                //mostrarToast(context, "falha ao obter o produto" )
                //Toast.makeText(this@QrCodeActivity, "falha ao obter o produto", Toast.LENGTH_LONG).show()
                Log.e("QrCodeActivity", "ObterProdutoQrCode", t)
            }
        }
        call.enqueue(callback)
    }

    private fun addProduto(data: CartRequest?) {
        val service = retrofit.create(CartsService::class.java)
        Log.i("data", data.toString())
        val call = data?.let { service.adicionarProdutoCarrinho(it) }

        val callback = object : Callback<CartResponse> {
            override fun onResponse(call: Call<CartResponse>, response: Response<CartResponse>) {
                if(response.isSuccessful){
                    Log.i("RESPONSE", response.body().toString())
                    mostrarToast(this@QrCodeActivity, "Produto adicionado ao carrinho.")
                } else{
                    mostrarToast(this@QrCodeActivity, "Não foi possível adicionar ao carrinho.")
                    response.errorBody()?.let {
                        Log.e("ERROR", it.string())
                    }
                }
            }

            override fun onFailure(call: Call<CartResponse>, t: Throwable) {
                Snackbar.make(binding.scannerView, "Não foi possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ERROR", "Falha ao executar serviço", t)
            }
        }

        call?.enqueue(callback)
    }


    private fun mostrarDialogoPermissaoCamera(){
        AlertDialog.Builder(this)
            .setTitle("Permissão da camera")
            .setMessage("Habilite a permissão do uso da camêra em configurações para ler o QR CODE")
            .setCancelable(false)
            .setPositiveButton("Ir para Configurações"){ dialogInterface, i ->
                val i = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                i.data = Uri.fromParts("package", packageName, null)
                startActivity(i)
                setResult(RESULT_CANCELED)
                finish()
            }
            .setNegativeButton("Cancelar"){dialogInterface, i ->
                setResult(RESULT_CANCELED)
                finish()
            }
            .create()
            .show()
    }
}