package com.github.charleslzq.baiduface.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RequestMapping(path = [""], method = [RequestMethod.GET])
annotation class List

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RequestMapping(path = [""], method = [RequestMethod.POST])
annotation class Add

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RequestMapping(path = ["/{id}"], method = [RequestMethod.GET])
annotation class Get

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RequestMapping(path = ["/{id}"], method = [RequestMethod.PUT])
annotation class Update

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@RequestMapping(path = ["/{id}"], method = [RequestMethod.DELETE])
annotation class Delete