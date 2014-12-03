//
// author: Cosmin Basca
//
// Copyright 2010 University of Zurich
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//        http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//
package com.simplehttp

/**
 * Created by basca on 04/06/14.
 */

/**
 * a non-exhaustive list of (useful) Mimetypes
 */
object MimeTypes extends Enumeration {
  type MimeTypes = Value

  val BinaryMsgpack = Value("application/x-msgpack")
  val Text = Value("text/plain")
  val Compressed7z = Value("application/x-7z-compressed")
  val Atom = Value("application/atom")
  val AtomcatXml = Value("application/atomcat+xml")
  val AtomservXml = Value("application/atomserv+xml")
  val BasicAudio = Value("audio/basic")
  val MsVideo = Value("video/x-msvideo")
  val Binary = Value("application/octet-stream")
  val Bmp = Value("image/x-ms-bmp")
  val Css = Value("text/css")
  val Csv = Value("text/csv")
  val Gif = Value("image/gif")
  val Gnumeric = Value("application/x-gnumeric")
  val Gtar = Value("application/x-gtar")
  val Hdf = Value("application/x-hdf")
  val MacBinhex = Value("application/mac-binhex40")
  val Html = Value("text/html")
  val Ico = Value("image/x-icon")
  val Ics = Value("text/calendar")
  val Iso = Value("application/x-iso9660-image")
  val Jar = Value("application/java-archive")
  val Jnlp = Value("application/x-java-jnlp-file")
  val Jpeg = Value("image/jpeg")
  val Jpe = Value("image/jpeg")
  val Jpg = Value("image/jpeg")
  val Js = Value("application/x-javascript")
  val Midi = Value("audio/midi")
  val PgpKeys = Value("application/pgp-keys")
  val Lzh = Value("application/x-lzh")
  val Lzx = Value("application/x-lzx")
  val MpegUrl = Value("audio/x-mpegurl")
  val Mp4 = Value("video/mp4")
  val Mpeg = Value("video/mpeg")
  val AudioOgg = Value("audio/ogg")
  val Ogg = Value("application/ogg")
  val Diff = Value("text/x-diff")
  val Pbm = Value("image/x-portable-bitmap")
  val Pcx = Value("image/pcx")
  val Pdf = Value("application/pdf")
  val Png = Value("image/png")
  val Ps = Value("application/postscript")
  val RdfXml = Value("application/rdf+xml")
  val RssXml = Value("application/rss+xml")
  val Rtf = Value("application/rtf")
  val Svg = Value("image/svg+xml")
  val Tar = Value("application/x-tar")
  val Tiff = Value("image/tiff")
  val Bittorrent = Value("application/x-bittorrent")
  val Wave = Value("audio/x-wav")
  val Xml = Value("application/xml")
  val Zip = Value("application/zip")
}
