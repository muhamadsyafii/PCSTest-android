package id.syafii.pcstest.utils.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import id.syafii.pcstest.databinding.DialogImagePreviewBinding
import id.syafii.pcstest.utils.ext.debounceClick
import id.syafii.pcstest.utils.ext.loadImageCenterCrop

class ImagePreviewDialogFragment : DialogFragment() {

  companion object {
    private const val IMAGE_URL = "image_url"
    fun newInstance(imageUrl: String): ImagePreviewDialogFragment {
      val fragment = ImagePreviewDialogFragment()
      val args = Bundle()
      args.putString(IMAGE_URL, imageUrl)
      fragment.arguments = args
      return fragment
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    val binding = DialogImagePreviewBinding.inflate(inflater, container, false)
    val imageUrl = arguments?.getString(IMAGE_URL) ?: ""

    binding.photoView.loadImageCenterCrop(requireContext(), imageUrl)

    binding.ivClose.debounceClick { dismiss() }
    return binding.root
  }

  override fun onStart() {
    super.onStart()
    dialog?.window?.apply {
      setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
      setBackgroundDrawable(ColorDrawable(Color.BLACK))
    }
  }

}
