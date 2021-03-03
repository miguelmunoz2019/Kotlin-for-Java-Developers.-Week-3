package nicestring

fun String.isNice(): Boolean {
   var condition1= !this.contains("bu",false)&&!this.contains("ba",false)&&!this.contains("be",false)
   var condition2= "aeiou".sumBy { ch->this.count{it == ch}} >=3
   var condition3= this.zipWithNext().count { it.first==it.second }>0
    return (condition1&&condition2)||(condition1&&condition3)||(condition3&&condition2)
}
