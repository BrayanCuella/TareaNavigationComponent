package com.cuellar.navigationcomponentexample
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cuellar.navigationcomponentexample.databinding.FragmentFirstBinding

const val REQUEST_CODE_PERMISSIONS = 100
val REQUIRED_PERMISSIONS = arrayOf(
    Manifest.permission.RECORD_AUDIO,
    Manifest.permission.WRITE_EXTERNAL_STORAGE,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.CAMERA // Permiso de la cámara
)

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Solicitar permisos al abrir el fragmento
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        // Acción del botón de navegación
        binding.btnNavigate.setOnClickListener {
            findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToSecondFragment(
                    name = "00:00:00"
                )
            )
        }

        // Acción del botón de cámara
        binding.btnNavigate1.setOnClickListener {
            findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToThirdFragment()
            )
        }

        // Acción del botón de CRUD
        binding.btnNavigate2.setOnClickListener {
            findNavController().navigate(
                FirstFragmentDirections.actionFirstFragmentToFourFragment()
            )
        }
    }

    // Manejo de resultados de permisos
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                // Mostrar un mensaje o cerrar la aplicación si los permisos son esenciales
                Toast.makeText(
                    requireContext(),
                    "Los permisos son necesarios para usar esta aplicación",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Verificación si todos los permisos han sido concedidos
    private fun allPermissionsGranted(): Boolean {
        return REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
