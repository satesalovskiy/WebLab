package ext

fun String.setPath(path: String) = this.concat(path)

fun String.addPathParam(param:Any) = this.concat("/$param")