package uz.medion.ui.main

import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import uz.medion.R
import uz.medion.databinding.ActivityMainBinding
import uz.medion.ui.base.BaseActivity
import uz.medion.utils.ViewUtils

class MainActivity : BaseActivity<ActivityMainBinding, MainVM>() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var bottomNavController: BottomNavController
    private lateinit var navController: NavController

    override fun onBound() {
        setUp()
    }

    private fun setUp() {
        val navHost = supportFragmentManager.findFragmentById(R.id.main_nav_controller)


        if (navHost != null) {
            navController = navHost.findNavController()
        }
        bottomNavController =
            BottomNavController(binding, binding.partialBottomNav, this, navController)
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
            if (destination.id == R.id.ourDoctorsFragment) {
                binding.tvMain.setText(R.string.our_doctors)
                ViewUtils.fadeIn(binding.ivBackArrow)
                ViewUtils.fadeIn(binding.tvMain)
                ViewUtils.fadeIn(binding.ivSearch)
                ViewUtils.fadeOut(binding.ivNotification)
                ViewUtils.fadeOut(binding.ivHeart)
                ViewUtils.fadeOut(binding.ivMedion)
            }
            //here goes AboutDoctorsFragment
            if (destination.id == R.id.aboutDoctorFragment) {
                binding.tvMain.setText(R.string.our_doctors)
                ViewUtils.fadeIn(binding.ivBackArrow)
                ViewUtils.fadeIn(binding.ivHeart)
                ViewUtils.fadeOut(binding.tvMain)
                ViewUtils.fadeOut(binding.ivMedion)
                ViewUtils.fadeOut(binding.ivSearch)
                ViewUtils.fadeOut(binding.ivNotification)
            }
            //here goes CertificateFragment
            if (destination.id == R.id.certificateFragment) {
                binding.tvMain.setText(R.string.image)
                ViewUtils.fadeIn(binding.ivBackArrow)
                ViewUtils.fadeIn(binding.tvMain)
                ViewUtils.fadeOut(binding.ivHeart)
                ViewUtils.fadeOut(binding.ivMedion)
                ViewUtils.fadeOut(binding.ivSearch)
                ViewUtils.fadeOut(binding.ivNotification)
            }
            //here goes PersonalAccountFragment
            if (destination.id == R.id.personalAccountFragment) {
                binding.tvMain.setText(R.string.personal_room)
                ViewUtils.fadeIn(binding.ivBackArrow)
                ViewUtils.fadeIn(binding.ivNotification)
                ViewUtils.fadeIn(binding.tvMain)
                ViewUtils.fadeOut(binding.ivHeart)
                ViewUtils.fadeOut(binding.ivMedion)
                ViewUtils.fadeOut(binding.ivSearch)
            }
        }
        binding.ivBackArrow.setOnClickListener { onBackPressed() }

        binding.nvNavigationDrawer.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> navController.navigate(R.id.homeFragment)
                R.id.nav_our_doctors -> navController.navigate(R.id.ourDoctorsFragment)
                R.id.nav_personal_account -> navController.navigate(R.id.personalAccountFragment)
            }
            binding.dlMenu.closeDrawer(GravityCompat.START)
            true
        }

    }


    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_main
    override val vm: MainVM by viewModels()

    override fun setStatusBarBackgroundHeight(statusBarBackground: View) {

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        val navigationOptions =
            NavOptions.Builder().setPopUpTo(navController.currentDestination!!.id, true).build()
//        if (navController.currentDestination?.id == R.id.olympiadRegistrationDoneFragment) {
//            navController.popBackStack(R.id.olympiadFragment, false)
//            // navController.navigate(R.id.olympiadFragment, null, navigationOptions)
        if (binding.dlMenu.isDrawerOpen(GravityCompat.START)) {
            binding.dlMenu.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
        if (!bottomNavController.onBackPressed()) {
            bottomNavController.onBackPressed()
            super.onBackPressed()
        }
    }


}