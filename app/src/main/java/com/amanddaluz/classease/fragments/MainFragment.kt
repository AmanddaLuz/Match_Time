package com.amanddaluz.classease.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.amanddaluz.classease.R
import com.amanddaluz.classease.customview.flipper.PosterFlipper
import com.amanddaluz.classease.customview.gesture.GestureListener
import com.amanddaluz.classease.databinding.FragmentMainBinding
import com.amanddaluz.classease.fragments.pageradapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var binding : FragmentMainBinding
    private val tabTitles = listOf("Cadastro", "Restrições", "Preferências")
    private val posterList: MutableList<PosterFlipper.PosterDomain> = mutableListOf(
        PosterFlipper.PosterDomain(
            "Teste 1", R.drawable.ic_launcher_foreground
        ),
        PosterFlipper.PosterDomain(
            "Teste 2", R.drawable.ic_launcher_background
        )
    )

    private val fragments = listOf(RegistrationFragment(), RestrictionsFragment(), PreferencesFragment())

    override fun onCreateView(
        inflater : LayoutInflater , container : ViewGroup? ,
        savedInstanceState : Bundle?
    ) : View {
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view : View , savedInstanceState : Bundle?) {
        super.onViewCreated(view , savedInstanceState)
        initToolbar()
        initViewPager()
        initTabLayout()
        setLayout()
        swipeAction()
    }

    private fun initTabLayout(){
        val tabIndicatorColors = arrayOf(R.color.gray, R.color.white, R.color.black)

        TabLayoutMediator(binding.tabLayout, binding.viewPager){tab, position ->
            tab.text = tabTitles[position]
        }.attach()

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(requireContext(), tabIndicatorColors[position]))
            }
        })
    }

    private fun initToolbar(){
        //binding.toolbar.title = "MatchTime"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
    }

    private fun initViewPager(){
        val pagerAdapter = ViewPagerAdapter(context as FragmentActivity, fragments)
        binding.run {
            viewPager.adapter = pagerAdapter
        }
    }

    private fun addDots() {
        binding.dots.setLayout(posterList.size, binding.flipper.displayedChild)
    }

    private fun swipeAction() {
        val gestureDetector = GestureDetector(requireContext(), GestureListener({ goNext() }, { goPrevious() }, { addDots() }))
        setTouch(gestureDetector)
    }

    private fun goPrevious() {
        binding.flipper.run {
            this.setInAnimation(requireContext(), R.anim.slide_in_left)
            this.showPrevious()
        }
    }

    private fun goNext() {
        binding.flipper.run {
            this.setInAnimation(requireContext(), R.anim.slide_in_right)
            this.showNext()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouch(gestureDetector: GestureDetector) {
        binding.flipper.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }
    }

    private fun setLayout() {
        with(binding.flipper) {
            setList(posterList)
            setLayout()
            addDots()
        }
    }

}