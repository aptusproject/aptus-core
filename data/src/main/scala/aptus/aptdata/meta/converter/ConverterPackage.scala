package aptus
package aptdata
package meta

import aptdata.aillag.data.json.GsonParsing

// ===========================================================================
package object converter {
  private[converter] type EnumValue = aptus.aptdata.meta.basic.EnumValue
  private[converter] val  EnumValue = aptus.aptdata.meta.basic.EnumValue

  // ---------------------------------------------------------------------------
  private[converter] type _Enm = aptus.aptdata.meta.basic.BasicType._Enm
  private[converter] val  _Enm = aptus.aptdata.meta.basic.BasicType._Enm

  // ===========================================================================
  def clsFromFile(schemaFilePath: FilePath): Cls =
    schemaFilePath
      .readFileContent()
      .pipe(GsonParsing.parseObject) // TODO: support more than just JSON
      .pipe(DataToMetaConverter.dynToCls)

  // ---------------------------------------------------------------------------
  def clsFromString(value: JsonObjectString): Cls =
    value
      .pipe(GsonParsing.parseObject) // TODO: support more than just JSON
      .pipe(DataToMetaConverter.dynToCls)

  // ===========================================================================
  private[converter] object MetaKey {
    val _fields   = "fields"
      val _key      = "key"
      val _info     = "info"
        val _optional = "optional"
        val _union    = "union"
          val _multiple  = "multiple"
          val _valueType = "valueType" } }

// ===========================================================================