package uz.medion.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import uz.medion.R
import uz.medion.databinding.ActivityMainBinding
import uz.medion.ui.base.BaseActivity
import uz.medion.utils.ViewUtils
import uz.medion.utils.gone

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {

    private lateinit var bottomNavController: BottomNavController
    private lateinit var navController: NavController

    override fun onBound() {
        setUp()
    }

    private fun setUp() {
        val navHost =
            supportFragmentManager.findFragmentById(R.id.main_nav_controller)
        if (navHost != null) {
            navController = navHost.findNavController()
        }
        bottomNavController = BottomNavController(binding.partialBottomNav, this, navController)
        binding.ivBackArrow.setOnClickListener {
            onBackPressed()
        }
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            //from where bottom navigation should be removed
            if (destination.id == R.id.aboutDoctorFragment) {
                ViewUtils.fadeOut(binding.partialBottomNav.root)
            } else {
                ViewUtils.fadeIn(binding.partialBottomNav.root)
            }

            //here goes HomeFragment
            if (destination.id == R.id.homeFragment) {
                ViewUtils.fadeIn(binding.ivMedion)
                ViewUtils.fadeIn(binding.ivNotification)
                ViewUtils.fadeOut(binding.ivBackArrow)
                ViewUtils.fadeOut(binding.tvMain)
                ViewUtils.fadeOut(binding.ivSearch)
                ViewUtils.fadeOut(binding.ivHeart)
            }
            //here goes OurDoctorsFragment
            if(destination.id == R.id.ourDoctorsFragment){
                binding.tvMain.setText(R.string.our_doctors)
                ViewUtils.fadeIn(binding.ivBackArrow)
                ViewUtils.fadeIn(binding.tvMain)
                ViewUtils.fadeIn(binding.ivSearch)
                ViewUtils.fadeOut(binding.ivNotification)
                ViewUtils.fadeOut(binding.ivHeart)
                ViewUtils.fadeOut(binding.ivMedion)
            }
            //here goes AboutDoctorsFragment
            if(destination.id == R.id.aboutDoctorFragment){
                binding.tvMain.setText(R.string.our_doctors)
                ViewUtils.fadeIn(binding.ivBackArrow)
                ViewUtils.fadeIn(binding.ivHeart)
                ViewUtils.fadeOut(binding.tvMain)
                ViewUtils.fadeOut(binding.ivMedion)
                ViewUtils.fadeOut(binding.ivSearch)
                ViewUtils.fadeOut(binding.ivNotification)
            }
            //here goes CertificateFragment
            if(destination.id == R.id.certificateFragment){
                binding.tvMain.setText(R.string.image)
                ViewUtils.fadeIn(binding.ivBackArrow)
                ViewUtils.fadeIn(binding.tvMain)
                ViewUtils.fadeOut(binding.ivHeart)
                ViewUtils.fadeOut(binding.ivMedion)
                ViewUtils.fadeOut(binding.ivSearch)
                ViewUtils.fadeOut(binding.ivNotification)
            }
        }

    }

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_main
    override val vm: MainVM by viewModels()

    override fun setStatusBarBackgroundHeight(statusBarBackground: View) {

    }

    override fun onBackPressed() {
        val navigationOptions =
            NavOptions.Builder().setPopUpTo(navController.currentDestination!!.id, true).build()
//        if (navController.currentDestination?.id == R.id.olympiadRegistrationDoneFragment) {
//            navController.popBackStack(R.id.olympiadFragment, false)
//            // navController.navigate(R.id.olympiadFragment, null, navigationOptions)
        if(!bottomNavController.onBackPressed()){
            bottomNavController.onBackPressed()
            super.onBackPressed()
        }

    }


}